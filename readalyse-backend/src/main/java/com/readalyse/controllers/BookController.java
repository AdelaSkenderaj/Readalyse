package com.readalyse.controllers;

import com.readalyse.api.BooksApi;
import com.readalyse.book.BookMapper;
import com.readalyse.model.Book;
import com.readalyse.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController implements BooksApi {

  private final BookMapper bookMapper;
  private final BookService bookService;

  @Override
  public ResponseEntity<Book> getBookById(Long bookId) {
    return ResponseEntity.ok(bookMapper.entityToModel(bookService.getBookById(bookId)));
  }
}
