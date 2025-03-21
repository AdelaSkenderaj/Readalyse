package com.readalyse.controllers;

import com.readalyse.book.BookEntity;
import com.readalyse.book.BookRepository;
import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartController {

  private final BookRepository bookRepository;

  private final BookService bookService;

  @GetMapping("/books")
  public List<BookEntity> findAll() {
    Page<BookEntity> books = bookRepository.findAll(PageRequest.of(0, 100));
    return books.getContent();
  }

  @GetMapping("/book/{bookId}/textbook")
  public String getText(@PathVariable Long bookId) {
    return bookService.getText(bookId);
  }

  @GetMapping("/book/{bookId}/scores")
  public ReadabilityScoresEntity getScores(@PathVariable Long bookId) {
    return bookService.getScores(bookId);
  }
}
