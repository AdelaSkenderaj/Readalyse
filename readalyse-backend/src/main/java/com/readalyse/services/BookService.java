package com.readalyse.services;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.entities.ResourceEntity;
import com.readalyse.repositories.BookRepository;
import com.readalyse.repositories.ReadabilityScoresRepository;
import com.readalyse.utility.AnalyzeText;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AnalyzeText analyzeText;

  private final ReadabilityScoresRepository readabilityScoresRepository;

  private final Logger logger;

  @Autowired
  public BookService(
      @Qualifier("ReadabilityScoresAnalysisLogger") Logger logger,
      BookRepository bookRepository,
      AnalyzeText analyzeText,
      ReadabilityScoresRepository readabilityScoresRepository) {
    this.logger = logger;
    this.bookRepository = bookRepository;
    this.analyzeText = analyzeText;
    this.readabilityScoresRepository = readabilityScoresRepository;
  }

  public String getText(BookEntity book) {
    ResourceEntity plaintextResource =
        book.getResources().stream()
            .filter(resourceEntity -> resourceEntity.getUrl().endsWith(".txt.utf-8"))
            .findFirst()
            .orElse(
                book.getResources().stream()
                    .filter(resourceEntity -> resourceEntity.getUrl().endsWith(".txt"))
                    .findFirst()
                    .orElse(null));
    if (plaintextResource == null) {
      logger.info(
          "Cannot find a resource for the book you are requesting. BookId: " + book.getId());
    }

    RestTemplate restTemplate = new RestTemplate();
    restTemplate
        .getMessageConverters()
        .add(
            0,
            new StringHttpMessageConverter(
                plaintextResource.getType().contains("us-ascii")
                    ? StandardCharsets.US_ASCII
                    : plaintextResource.getType().contains("iso-8859-1")
                        ? StandardCharsets.ISO_8859_1
                        : StandardCharsets.UTF_8));
    String text = restTemplate.getForObject(plaintextResource.getUrl(), String.class);
    if (text == null) {
      logger.info("No text was received from the source! BookId" + book.getId());
    }
    return text;
  }

  public String getText(Long bookId) {
    BookEntity book =
        bookRepository
            .findById(bookId)
            .orElseThrow(
                () -> {
                  throw new RuntimeException("Book not found");
                });
    return getText(book);
  }

  public ReadabilityScoresEntity getScores(Long bookId) {
    String text = getText(bookId);
    System.out.println("starting score analysis");
    return analyzeText.calculateScores(text);
  }

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveScores(BookEntity book) {
    Optional<ReadabilityScoresEntity> readabilityScores =
        readabilityScoresRepository.findByBookId(book.getId());
    if (readabilityScores.isEmpty() && book.getType().equals("text")) {
      logger.info("Reading score analysis for book " + book.getId());
      String text = getText(book);
      ReadabilityScoresEntity scores = analyzeText.calculateScores(text);
      scores.setBookId(book.getId());
      logger.info("Saving scores for book " + book.getId());
      readabilityScoresRepository.save(scores);
    }
  }

  public BookEntity getBookById(Long bookId) {
    return bookRepository
        .findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));
  }
}
