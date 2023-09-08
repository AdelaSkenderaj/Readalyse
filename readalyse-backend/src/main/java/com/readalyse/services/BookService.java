package com.readalyse.services;


import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ResourceEntity;
import com.readalyse.repositories.BookRepository;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

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
    return text;
  }
}
