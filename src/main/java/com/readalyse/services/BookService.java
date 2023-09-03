package com.readalyse.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
  private final ObjectMapper objectMapper;

  public String getText(Long bookId) {
    BookEntity book =
        bookRepository
            .findById(bookId)
            .orElseThrow(
                () -> new IllegalArgumentException("Book with id " + bookId + " not found"));

    ResourceEntity plaintextResource =
        book.getResources().stream()
            .filter(resourceEntity -> resourceEntity.getType().contains("text/plain"))
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
    return text;
  }
}
