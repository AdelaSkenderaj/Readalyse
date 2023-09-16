package com.readalyse.utility;

import com.readalyse.entities.*;
import com.readalyse.mappers.*;
import com.readalyse.repositories.*;
import com.readalyse.services.BookService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RiotException;
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
  private final SubjectRepository subjectRepository;
  private final ParseBookData parseBookData;
  private final BookMapper bookMapper;
  private final BookshelfMapper bookshelfMapper;
  private final LanguageMapper languageMapper;
  private final SubjectMapper subjectMapper;
  private final ResourceMapper resourceMapper;

  private final AgentMapper agentMapper;
  private final BookService bookService;

  private final AnalyzeText analyzeText;
  Logger logger = Logger.getLogger(InformationExtraction.class.getName());
  FileHandler fileHandler;

  private static String basePath = "C:/Users/Dela/test";

  public void extractInformation(File dirPath) {
    try {
      // This block configures the logger with handler and formatter
      fileHandler = new FileHandler("C:/Users/Dela/log/MyLogFile6.log");
      logger.addHandler(fileHandler);
      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);

      // the following statement is used to log any messages
      logger.info("My first log");

    } catch (SecurityException | IOException e) {
      e.printStackTrace();
    }
    File[] filesList = dirPath.listFiles();
    for (File file : filesList) {
      logger.info("********************** START OF BOOK ***********************");
      Long start = System.currentTimeMillis();
      BookEntity book = getBook(file.getPath());
      Long end = System.currentTimeMillis();
      if (book != null) {
          bookService.saveScores(book);
          logger.info("Book " + book.getId() + " took " + (end - start));
      }
      logger.info("\n\n\n**********************END OF BOOK *************************\n\n\n");
    }
  }

  public BookEntity getBook(String path) {
    //        String filePath = "C:/Users/Dela/test/pg" + path + ".rdf";
    Model model = ModelFactory.createDefaultModel();
    try {
      model.read(path);
    } catch (RiotException e) {
      e.printStackTrace();
      return null;
    }
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
    /*String text = bookService.getText(book);
    ReadabilityScoresEntity readabilityScores = analyzeText.calculateScores(text);
    book.setReadabilityScores(readabilityScores);*/
    return bookRepository.save(book);
  }

  private BookEntity getBaseBookInformation(Model model) {
    Long start = System.currentTimeMillis();
    BookEntity book = bookMapper.modelToEntity(parseBookData.getBaseBookInformation(model));
    Long end = System.currentTimeMillis();
    logger.info("Get base book " + " took " + (end - start));
    return book;
  }

  private List<BookshelfEntity> getBookshelves(Model model) {
    Long start = System.currentTimeMillis();
    List<BookshelfEntity> bookshelfEntities =
        parseBookData.getBookshelves(model).stream()
            .map(
                b -> {
                  BookshelfEntity bookshelf = bookshelfMapper.modelToEntity(b);
                  Optional<BookshelfEntity> existingBookshelf =
                      bookshelfRepository.findByName(bookshelf.getName());

                  if (existingBookshelf.isEmpty()) {
                    return bookshelf;
                  }

                  return existingBookshelf.get(); // Return the existing bookshelf
                })
            .collect(Collectors.toList());
    Long end = System.currentTimeMillis();
    logger.info("Get bookshelves " + " took " + (end - start));
    return bookshelfEntities;
  }

  private List<LanguageEntity> getLanguages(Model model) {
    Long start = System.currentTimeMillis();
    List<LanguageEntity> languages =
        parseBookData.getLanguages(model).stream()
            .map(
                l -> {
                  LanguageEntity language = languageMapper.modelToEntity(l);
                  Optional<LanguageEntity> existingLanguage =
                      languageRepository.findByLanguage(language.getLanguage());

                  if (existingLanguage.isEmpty()) {
                    return language;
                  }
                  return existingLanguage.get();
                })
            .collect(Collectors.toList());
    Long end = System.currentTimeMillis();
    logger.info("Get languages " + " took " + (end - start));
    return languages;
  }

  private List<SubjectEntity> getSubjects(Model model) {
    Long start = System.currentTimeMillis();
    List<SubjectEntity> subjects =
        parseBookData.getSubjects(model).stream()
            .map(
                s -> {
                  SubjectEntity subject = subjectMapper.modelToEntity(s);
                  Optional<SubjectEntity> existingSubject =
                      subjectRepository.findByName(subject.getName());

                  if (existingSubject.isEmpty()) {
                    return subject;
                  }
                  return existingSubject.get();
                })
            .collect(Collectors.toList());

    Long end = System.currentTimeMillis();
    logger.info("Get subjects  " + " took " + (end - start));
    return subjects;
  }

  private List<ResourceEntity> getResources(Model model) {
    Long start = System.currentTimeMillis();
    List<ResourceEntity> resources =
        resourceMapper.modelsToEntities(parseBookData.getResources(model));
    Long end = System.currentTimeMillis();
    logger.info("Get resources " + " took " + (end - start));
    return resources;
  }

  private List<AgentEntity> getAgents(Model model) {
    Long start = System.currentTimeMillis();
    List<AgentEntity> agents =
        parseBookData.getAgents(model).stream()
            .map(
                a -> {
                  AgentEntity agent = agentMapper.modelToEntity(a);
                  PersonEntity person =
                      personRepository
                          .findById(agent.getPerson().getId())
                          .orElseGet(() -> personRepository.save(agent.getPerson()));
                  AgentTypeEntity agentType =
                      agentTypeRepository
                          .findByName(agent.getType().getName())
                          .orElseGet(() -> agentTypeRepository.save(agent.getType()));
                  agent.setPerson(person);
                  agent.setType(agentType);
                  Optional<AgentEntity> existingAgent =
                      agentRepository.findByPersonAndType(person, agentType);
                  if (existingAgent.isEmpty()) {
                    return agent;
                  }
                  return existingAgent.get();
                })
            .collect(Collectors.toList());
    Long end = System.currentTimeMillis();
    logger.info("Get agents " + " took " + (end - start));
    return agents;
  }
}
