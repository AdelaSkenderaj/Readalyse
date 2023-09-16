package com.readalyse.services;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.entities.ResourceEntity;
import com.readalyse.repositories.BookRepository;
import com.readalyse.repositories.ReadabilityScoresRepository;
import com.readalyse.utility.AnalyzeText;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AnalyzeText analyzeText;

  private final ReadabilityScoresRepository readabilityScoresRepository;

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
      throw new RuntimeException("Cannot find a resource for the book you are requesting");
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
      throw new RuntimeException("No text was received from the source!");
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
  public void saveScores(BookEntity book) {
    String text = getText(book);
    ReadabilityScoresEntity scores = analyzeText.calculateScores(text);
    scores.setBookId(book.getId());
    readabilityScoresRepository.save(scores);
  }
}
