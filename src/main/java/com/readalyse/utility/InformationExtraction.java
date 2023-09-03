package com.readalyse.utility;

import static com.readalyse.utility.Constants.TERMS;

import com.readalyse.entities.*;
import com.readalyse.repositories.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationExtraction {

  private final BookRepository bookRepository;
  private final AgentRepository agentRepository;
  private final AgentTypeRepository agentTypeRepository;
  private final BookshelfRepository bookshelfRepository;
  private final LanguageRepository languageRepository;
  private final PersonRepository personRepository;
  private final ResourceRepository resourceRepository;
  private final SubjectRepository subjectRepository;

  Logger logger = Logger.getLogger(InformationExtraction.class.getName());
  FileHandler fileHandler;

  private static String basePath = "C:/Users/Dela/test";

  public void extractInformation(File dirPath) {
    try {
      // This block configure the logger with handler and formatter
      fileHandler = new FileHandler("C:/Users/Dela/log/MyLogFile.log");
      logger.addHandler(fileHandler);
      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);

      // the following statement is used to log any messages
      logger.info("My first log");

    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    File filesList[] = dirPath.listFiles();
    for (File file : filesList) {
      logger.info("********************** START OF BOOK ***********************");
      Long start = System.currentTimeMillis();
      BookEntity book = getBook(file.getPath());
      Long end = System.currentTimeMillis();
      logger.info("Book " + book.getId() + " took " + (end - start));
      logger.info("\n\n\n**********************END OF BOOK *************************\n\n\n");
    }
  }

  public BookEntity getBook(String path) {
    //        String filePath = "C:/Users/Dela/test/pg" + path + ".rdf";
    Model model = ModelFactory.createDefaultModel(); // create an empty model
    model.read(path);
    BookEntity book = getBaseBookInformation(model);
    List<BookshelfEntity> bookshelves = getBookshelves(model);
    List<LanguageEntity> languages = getLanguages(model);
    List<SubjectEntity> subjects = getSubjects(model);
    List<ResourceEntity> resources = getResources(model);
    List<AgentEntity> agents = getAgents(model);
    book.setBookshelves(bookshelves);
    book.setLanguages(languages);
    book.setSubjects(subjects);
    book.setResources(resources);
    book.setAgents(agents);
    return bookRepository.save(book);
  }

  private BookEntity getBaseBookInformation(Model model) {
    Long start = System.currentTimeMillis();
    BookEntity book = null;
    String queryString =
        TERMS
            + "SELECT ?ebook ?title ?description ?downloads\n"
            + "WHERE {\n"
            + "  ?ebook rdf:type pgterms:ebook.\n"
            + "  OPTIONAL { ?ebook dcterms:title ?title. }\n"
            + "  OPTIONAL { ?ebook dcterms:description ?description. }\n"
            + "  OPTIONAL { ?ebook pgterms:downloads ?downloads. }\n"
            + "}";

    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal title = soln.getLiteral("title");
        Literal description = soln.getLiteral("description");
        Literal downloads = soln.getLiteral("downloads");
        Resource ebook = soln.getResource("ebook");

        book = bookRepository.findById(Long.valueOf(formatValue(ebook))).orElse(null);

        if (Objects.isNull(book)) {
          book =
              BookEntity.builder()
                  .id(Long.valueOf(formatValue(ebook)))
                  .title(formatValue(title))
                  .description(formatValue(description))
                  .downloads(Long.valueOf(formatValue(downloads)))
                  .build();
          book = bookRepository.save(book);
        }
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get base book information " + " took " + (end - start));
    return book;
  }

  private List<BookshelfEntity> getBookshelves(Model model) {
    Long start = System.currentTimeMillis();
    List<BookshelfEntity> bookshelves = new ArrayList<>();
    String queryString =
        TERMS
            + "SELECT ?value\n"
            + "WHERE {\n"
            + "    ?ebook pgterms:bookshelf ?Description .\n"
            + "    OPTIONAL { ?Description rdf:value ?value. }\n"
            + "}";

    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        BookshelfEntity bookshelf = bookshelfRepository.findByName(formatValue(value));

        if (Objects.isNull(bookshelf)) {
          bookshelf = BookshelfEntity.builder().name(formatValue(value)).build();

          bookshelf = bookshelfRepository.save(bookshelf);
        }
        bookshelves.add(bookshelf);
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get bookshelves " + " took " + (end - start));
    return bookshelves;
  }

  private List<LanguageEntity> getLanguages(Model model) {
    Long start = System.currentTimeMillis();
    List<LanguageEntity> languages = new ArrayList<>();
    String queryString =
        TERMS
            + "SELECT ?value\n"
            + "WHERE {\n"
            + "    ?ebook dcterms:language ?Description .\n"
            + "    OPTIONAL { ?Description rdf:value ?value. }\n"
            + "}";
    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        LanguageEntity language = languageRepository.findByLanguage(formatValue(value));

        if (Objects.isNull(language)) {
          language = LanguageEntity.builder().language(formatValue(value)).build();

          language = languageRepository.save(language);
        }
        languages.add(language);
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get languages " + " took " + (end - start));
    return languages;
  }

  private List<SubjectEntity> getSubjects(Model model) {
    Long start = System.currentTimeMillis();
    List<SubjectEntity> subjects = new ArrayList<>();
    String queryString =
        TERMS
            + "SELECT ?value\n"
            + "WHERE {\n"
            + "    ?ebook dcterms:subject ?Description .\n"
            + "    OPTIONAL { ?Description rdf:value ?value. }\n"
            + "}";
    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        SubjectEntity subject = subjectRepository.findByName(formatValue(value));

        if (Objects.isNull(subject)) {
          subject = SubjectEntity.builder().name(formatValue(value)).build();

          subject = subjectRepository.save(subject);
        }
        subjects.add(subject);
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get subjects  " + " took " + (end - start));
    return subjects;
  }

  private List<ResourceEntity> getResources(Model model) {
    Long start = System.currentTimeMillis();
    List<ResourceEntity> resources = new ArrayList<>();
    String queryString =
        TERMS
            + "SELECT ?file ?extent ?modified ?value\n"
            + "WHERE {\n"
            + "  {\n"
            + "    SELECT ?ebook ?file\n"
            + "    WHERE {\n"
            + "      ?ebook dcterms:hasFormat ?file .\n"
            + "    }\n"
            + "  }\n"
            + "  OPTIONAL { ?file dcterms:extent ?extent. }\n"
            + "  OPTIONAL { ?file dcterms:modified ?modified. }\n"
            + "  OPTIONAL {\n"
            + "    ?file dcterms:format ?Description .\n"
            + "    ?Description rdf:value ?value .\n"
            + "  }\n"
            + "}";
    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal extent = soln.getLiteral("extent");
        Literal modified = soln.getLiteral("modified");
        Literal value = soln.getLiteral("value");
        Resource file = soln.getResource("file");

        String type = Optional.ofNullable(value).map(Literal::toString).orElse(null);

        ResourceEntity resource =
            ResourceEntity.builder()
                .url(Optional.ofNullable(file).map(Resource::toString).orElse(null))
                .size(Long.valueOf(formatValue(extent)))
                .modified(LocalDateTime.parse(formatValue(modified)))
                .type(
                    type != null && type.contains("^")
                        ? type.substring(0, type.indexOf('^'))
                        : type)
                .build();

        resources.add(resource);
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get resources " + " took " + (end - start));
    return resources;
  }

  private List<AgentEntity> getAgents(Model model) {
    Long start = System.currentTimeMillis();
    List<AgentEntity> agents = new ArrayList<>();
    String queryString =
        TERMS
            + "SELECT ?agent ?predicate ?name ?birthdate ?deathdate (GROUP_CONCAT(?alias; SEPARATOR=\"; \") AS ?aliases) ?webpage\n"
            + "WHERE {\n"
            + " { "
            + "   ?ebook ?typeProperty ?agent . "
            + "   VALUES (?typeProperty ?type) {\n"
            + "    (dcterms:creator          \"Creator\")\n"
            + "    (marcrel:ill              \"Illustrator\")\n"
            + "    (marcrel:pht              \"Photographer\")\n"
            + "    (marcrel:ann              \"Annotator\")\n"
            + "    (marcrel:cmm              \"Contributor\")\n"
            + "    (marcrel:trl              \"Translator\")\n"
            + "    (marcrel:com              \"Compiler\")\n"
            + "    (marcrel:cmp              \"Composer\")\n"
            + "    (marcrel:prf              \"Performer\")\n"
            + "    (marcrel:edt              \"Editor\")\n"
            + "    (marcrel:ctb              \"Contributor\")\n"
            + "    (marcrel:aui              \"Author Of Introduction\")\n"
            + "  } "
            + "     BIND(COALESCE(?type, \"\") AS ?predicate)\n "
            + " } "
            + "  OPTIONAL { ?agent pgterms:name ?name. }\n"
            + "  OPTIONAL { ?agent pgterms:birthdate ?birthdate. }\n"
            + "  OPTIONAL { ?agent pgterms:deathdate ?deathdate. }\n"
            + "  OPTIONAL { ?agent pgterms:alias ?alias. }\n"
            + "  OPTIONAL {\n"
            + "    SELECT ?agent ?webpage "
            + "    WHERE { "
            + "       ?agent pgterms:webpage ?webpage .\n"
            + "    } "
            + "    LIMIT 1 "
            + "  }\n"
            + "}\n"
            + "GROUP BY ?agent ?predicate ?name ?birthdate ?deathdate ?webpage";

    Query query = QueryFactory.create(queryString);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal name = soln.getLiteral("name");
        Literal birthdate = soln.getLiteral("birthdate");
        Literal deathdate = soln.getLiteral("deathdate");
        Literal alias = soln.getLiteral("aliases");
        Resource webpage = soln.getResource("webpage");
        Resource id = soln.getResource("agent");
        Literal predicate = soln.getLiteral("predicate");

        Long birthdateValue = birthdate != null ? Long.parseLong(formatValue(birthdate)) : null;
        Long deathdateValue = deathdate != null ? Long.parseLong(formatValue(deathdate)) : null;

        AgentTypeEntity agentType = agentTypeRepository.findByName(formatValue(predicate));
        if (Objects.isNull(agentType)) {
          agentType = AgentTypeEntity.builder().name(formatValue(predicate)).build();
          agentType = agentTypeRepository.save(agentType);
        }

        PersonEntity person = personRepository.findById(Long.valueOf(formatValue(id))).orElse(null);
        if (Objects.isNull(person)) {
          person =
              PersonEntity.builder()
                  .id(Long.valueOf(formatValue(id)))
                  .name(formatValue(name))
                  .alias(formatValue(alias))
                  .birthdate(birthdateValue)
                  .deathdate(deathdateValue)
                  .webpage(Optional.ofNullable(webpage).map(Resource::toString).orElse(null))
                  .build();
          person = personRepository.save(person);
        }

        AgentEntity agent = agentRepository.findByPersonAndType(person, agentType);
        if (Objects.isNull(agent)) {
          agent = AgentEntity.builder().person(person).type(agentType).build();
          agent = agentRepository.save(agent);
        }
        agents.add(agent);
      }
    }
    Long end = System.currentTimeMillis();
    logger.info("Get agents " + " took " + (end - start));
    return agents;
  }

  private String formatValue(Literal value) {
    String formattedValue = Optional.ofNullable(value).map(Literal::getString).orElse(null);
    if (formattedValue != null && formattedValue.contains("^^")) {
      formattedValue = formattedValue.substring(0, formattedValue.indexOf('^'));
    }

    if (formattedValue != null && formattedValue.contains("/")) {
      formattedValue = formattedValue.substring(formattedValue.lastIndexOf('/') + 1);
    }
    return formattedValue;
  }

  private static String formatValue(Resource value) {
    String formattedValue = Optional.ofNullable(value).map(Resource::toString).orElse(null);
    if (formattedValue != null && formattedValue.contains("^^")) {
      formattedValue = formattedValue.substring(0, formattedValue.indexOf('^'));
    }

    if (formattedValue != null && formattedValue.contains("/")) {
      formattedValue = formattedValue.substring(formattedValue.lastIndexOf('/') + 1);
    }
    return formattedValue;
  }
}
