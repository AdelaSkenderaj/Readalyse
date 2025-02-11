package com.readalyse.dataImport;

import static com.readalyse.utility.Constants.SPARQL_QUERY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readalyse.agent.AgentEntity;
import com.readalyse.agent.AgentRepository;
import com.readalyse.agentType.AgentTypeEntity;
import com.readalyse.agentType.AgentTypeRepository;
import com.readalyse.book.BookEntity;
import com.readalyse.book.BookRepository;
import com.readalyse.bookshelf.BookshelfEntity;
import com.readalyse.bookshelf.BookshelfRepository;
import com.readalyse.language.LanguageEntity;
import com.readalyse.language.LanguageRepository;
import com.readalyse.person.PersonEntity;
import com.readalyse.person.PersonRepository;
import com.readalyse.resource.ResourceEntity;
import com.readalyse.subject.SubjectEntity;
import com.readalyse.subject.SubjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractFileInformationService {

  private static final Logger logger =
      Logger.getLogger(ExtractFileInformationService.class.getName());
  private final PersonRepository personRepository;

  @PersistenceContext private EntityManager entityManager;

  private final LanguageRepository languageRepository;
  private final BookshelfRepository bookshelfRepository;
  private final SubjectRepository subjectRepository;
  private final AgentTypeRepository agentTypeRepository;
  private final AgentRepository agentRepository;
  private final BookRepository bookRepository;

  @Transactional
  public void extractFileInformation(Model model) {
    Query query = QueryFactory.create(SPARQL_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet results = qexec.execSelect();

      if (results.hasNext()) {
        QuerySolution solution = results.nextSolution();
        BookEntity bookEntity = parseBaseBookInformation(solution);
        bookEntity.setLanguages(parseLanguages(solution));
        bookEntity.setBookshelves(parseBookshelves(solution));
        bookEntity.setSubjects(parseSubjects(solution));
        bookEntity.setResources(parseResources(solution, bookEntity));
        bookEntity.setAgents(parseAgents(solution, bookEntity));
        try {
          BookEntity saved = bookRepository.save(bookEntity);
        } catch (Exception e) {
          logger.severe(
              "Failed to save book " + bookEntity.getId() + " with exception " + e.getMessage());
        }
      }
    }
  }

  private BookEntity parseBaseBookInformation(QuerySolution querySolution) {
    return BookEntity.builder()
        .id(
            querySolution.contains("ebookId")
                ? Long.parseLong(querySolution.getLiteral("ebookId").getString())
                : 0L)
        .title(querySolution.contains("title") ? querySolution.getLiteral("title").getString() : "")
        .description(
            querySolution.contains("descriptions")
                ? querySolution.getLiteral("descriptions").getString()
                : "")
        .summary(
            querySolution.contains("summary")
                ? querySolution.getLiteral("summary").getString()
                : "")
        .downloads(
            querySolution.contains("downloads")
                ? Long.parseLong(querySolution.getLiteral("downloads").getString())
                : 0L)
        .build();
  }

  private List<LanguageEntity> parseLanguages(QuerySolution queryResult) {
    Literal languageLiteral = queryResult.getLiteral("languages");

    if (languageLiteral == null || languageLiteral.getString().isEmpty()) {
      return Collections.emptyList();
    }

    String[] languageArray = languageLiteral.getString().split("SEP");
    Set<String> languageSet = new HashSet<>(Arrays.asList(languageArray));
    List<LanguageEntity> existingLanguages = languageRepository.findByLanguageIn(languageSet);

    Map<String, LanguageEntity> languageMap =
        existingLanguages.stream()
            .collect(Collectors.toMap(LanguageEntity::getLanguage, entity -> entity));

    return Arrays.stream(languageArray)
        .map(language -> languageMap.getOrDefault(language, new LanguageEntity(language)))
        .toList();
  }

  private List<BookshelfEntity> parseBookshelves(QuerySolution queryResult) {
    Literal bookshelfLiteral = queryResult.getLiteral("bookshelves");

    if (bookshelfLiteral == null || bookshelfLiteral.getString().isEmpty()) {
      return Collections.emptyList();
    }

    String[] bookshelfArray = bookshelfLiteral.getString().split("SEP");
    Set<String> bookshelfSet = new HashSet<>(Arrays.asList(bookshelfArray));
    List<BookshelfEntity> existingBookshelves = bookshelfRepository.findByNameIn(bookshelfSet);

    Map<String, BookshelfEntity> bookshelfMap =
        existingBookshelves.stream()
            .collect(Collectors.toMap(BookshelfEntity::getName, entity -> entity));

    return Arrays.stream(bookshelfArray)
        .map(bookshelf -> bookshelfMap.getOrDefault(bookshelf, new BookshelfEntity(bookshelf)))
        .toList();
  }

  private List<SubjectEntity> parseSubjects(QuerySolution queryResult) {
    Literal subjectLiteral = queryResult.getLiteral("subjects");

    if (subjectLiteral == null || subjectLiteral.getString().isEmpty()) {
      return Collections.emptyList();
    }

    String[] subjectArray = subjectLiteral.getString().split("SEP");
    Set<String> subjectSet = new HashSet<>(Arrays.asList(subjectArray));
    List<SubjectEntity> existingSubjects = subjectRepository.findByNameIn(subjectSet);

    Map<String, SubjectEntity> subjectMap =
        existingSubjects.stream()
            .collect(Collectors.toMap(SubjectEntity::getName, entity -> entity));

    return Arrays.stream(subjectArray)
        .map(subject -> subjectMap.getOrDefault(subject, new SubjectEntity(subject)))
        .toList();
  }

  private List<ResourceEntity> parseResources(QuerySolution queryResult, BookEntity bookEntity) {
    Literal resourcesLiteral = queryResult.getLiteral("resources");

    if (resourcesLiteral == null || resourcesLiteral.getString().isEmpty()) {
      return Collections.emptyList();
    }

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(
          resourcesLiteral.getString(), new TypeReference<List<ResourceEntity>>() {});
    } catch (JsonProcessingException e) {
      logger.severe("Error processing the resources for book " + bookEntity.getId());
    }
    return Collections.emptyList();
  }

  @Transactional
  public List<AgentEntity> parseAgents(QuerySolution queryResult, BookEntity bookEntity) {
    Literal agentsLiteral = queryResult.getLiteral("agents");

    if (agentsLiteral == null || agentsLiteral.getString().isEmpty()) {
      return Collections.emptyList();
    }

    String[] agentsArray = agentsLiteral.getString().split("SEP");
    List<AgentEntity> agentEntities = new LinkedList<>();
    for (String agent : agentsArray) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> dataMap = objectMapper.readValue(agent, Map.class);

        PersonEntity personEntity =
            objectMapper.readValue(agent, new TypeReference<PersonEntity>() {});
        String type = dataMap.getOrDefault("role", null);

        AgentTypeEntity agentTypeEntity = agentTypeRepository.findByName(type).orElse(null);

        if (agentTypeEntity == null) {
          agentTypeEntity = new AgentTypeEntity(type);
          agentTypeEntity = agentTypeRepository.save(agentTypeEntity);
        } else {
          agentTypeEntity = entityManager.merge(agentTypeEntity);
        }

        PersonEntity existingPerson = personRepository.findById(personEntity.getId()).orElse(null);
        if (existingPerson == null) {
          personEntity = personRepository.save(personEntity);
        } else {
          personEntity = entityManager.merge(personEntity);
        }

        AgentEntity agentEntity =
            agentRepository
                .findByPersonAndType(personEntity, agentTypeEntity)
                .orElse(new AgentEntity(personEntity, agentTypeEntity));

        agentEntities.add(agentEntity);

      } catch (JsonProcessingException e) {
        logger.severe("Error processing the agents for book " + bookEntity.getId());
      }
    }

    return agentEntities;
  }
}
