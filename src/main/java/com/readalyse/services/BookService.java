package com.readalyse.services;

import static com.readalyse.utility.Constants.PROJECT_GUTENBERG_MARKERS_PATTERN;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadabilityScores;
import com.readalyse.entities.ResourceEntity;
import com.readalyse.repositories.BookRepository;
import com.readalyse.utility.BookData;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  Pattern regex = Pattern.compile(PROJECT_GUTENBERG_MARKERS_PATTERN, Pattern.MULTILINE);

  public String getText(Long bookId) {

    BookEntity book =
        bookRepository
            .findById(bookId)
            .orElseThrow(
                () -> new IllegalArgumentException("Book with id " + bookId + " not found"));

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
    text = preprocessText(text);
    analyse(text);
    return text;
  }

  public String preprocessText(String text) {
    Matcher matcher = regex.matcher(text);
    String extractedText = "";
    if (matcher.find()) {
      extractedText = matcher.group(1).trim();
    }
    return !"".equals(extractedText) ? extractedText : text;
  }

  public BookData analyse(String text) {
    BookData data = new BookData(text);
    ReadabilityScores readabilityScores =
        ReadabilityScores.builder()
            .fleschKincaidGradeLevel(calculateFleschKincaidGradeLevel(data))
            .fleschReadingEase(calculateFleschReadingEase(data))
            .colemanLiauIndex(colemanLiauIndex(data))
            .smogIndex(smogIndex(data))
            .automatedReadabilityIndex(calculateAutomatedReabilityIndex(data))
            .forcastIndex(forcast(data))
            .lixIndex(lixFormula(data))
            .rixIndex(rixFormula(data))
            .build();
    System.out.println(readabilityScores);
    return data;
  }

  public Double calculateFleschKincaidGradeLevel(BookData data) {
    return (0.39 * ((double) data.getWords() / data.getSentences())
        + 11.8 * ((double) data.getNrSyllables() / data.getWords())
        - 15.59);
  }

  public Double calculateFleschReadingEase(BookData data) {
    return (206.835
        - 1.015 * ((double) data.getWords() / data.getSentences())
        - 84.6 * ((double) data.getNrSyllables() / data.getWords()));
  }

  public Double colemanLiauIndex(BookData data) {
    return ((0.0588 * data.getLetters() / data.getWords())
        - (0.296 * data.getSentences() / data.getWords()));
  }

  public Double smogIndex(BookData data) {
    return (1.0430 * Math.sqrt((double) data.getPolysyllabicWords() * 30 / data.getSentences())
        + 3.1291);
  }

  public Double calculateAutomatedReabilityIndex(BookData data) {
    return (4.71 * ((double) data.getLetters() / data.getWords())
        + 0.5 * ((double) data.getWords() / data.getSentences())
        - 21.43);
  }

  public Double forcast(BookData data) {
    return (20 - ((double) data.getSingleSyllableWords() / 1500));
  }

  /* public Double powersSumnerKearl(BookData data) {
    return (0.0778 * ((double) data.getWords() / data.getSentences())
        + 0.0455 * data.getNrSyllables()
        + 2.7971);
  }*/

  public Double lixFormula(BookData data) {
    return ((data.getLongWords() * 100 / data.getWords())
        + ((double) data.getWords() / data.getSentences()));
  }

  public Double rixFormula(BookData data) {
    return ((double) data.getLongWords() / data.getSentences());
  }
}
