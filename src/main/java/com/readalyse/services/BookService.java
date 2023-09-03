package com.readalyse.services;

import static com.readalyse.utility.Constants.PROJECT_GUTENBERG_MARKERS_PATTERN;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ResourceEntity;
import com.readalyse.repositories.BookRepository;
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
  Pattern regex = Pattern.compile(PROJECT_GUTENBERG_MARKERS_PATTERN, Pattern.DOTALL);

  public String getText(Long bookId) {
    BookEntity book =
        bookRepository
            .findById(bookId)
            .orElseThrow(
                () -> new IllegalArgumentException("Book with id " + bookId + " not found"));

    ResourceEntity plaintextResource =
        book.getResources().stream()
            .filter(resourceEntity -> resourceEntity.getUrl().contains(".txt.utf-8"))
            .findFirst()
            .orElse(null);
    if (plaintextResource == null) {
      throw new RuntimeException("Cannot find a resource for the book you are requesting");
    }

    RestTemplate restTemplate = new RestTemplate();
    restTemplate
        .getMessageConverters()
        .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    String text = restTemplate.getForObject(plaintextResource.getUrl(), String.class);
    return preprocessText(text);
  }

  public String preprocessText(String text) {
    Matcher matcher = regex.matcher(text);
    String extractedText = "";
    if (matcher.find()) {
      extractedText = matcher.group(1).trim();
    }
    return !"".equals(extractedText) ? extractedText : text;
  }
}
