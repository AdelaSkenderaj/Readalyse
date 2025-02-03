package com.readalyse.controllers;

import com.readalyse.dataImport.FileRetrieval;
import com.readalyse.dataImport.InformationExtraction;
import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.repositories.BookRepository;
import com.readalyse.services.BookService;
import java.io.File;
import java.io.IOException;
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

  private final InformationExtraction informationExtraction;

  private final BookRepository bookRepository;

  private final FileRetrieval fileRetrieval;

  private static String basePath = "C:/Users/Dela/test";
  private final BookService bookService;

  @GetMapping("/book")
  public void getBook() {
    informationExtraction.extractInformation(new File(basePath));
  }

  /* @GetMapping("/retrieveData")
  public void startAnalysis() throws IOException, ParserConfigurationException, SAXException {
    fileRetrieval.getRdfData();
  }*/

  @GetMapping("/retrieveData2")
  public void retrieveFiles() throws IOException {
    fileRetrieval.getRdfData();
  }

  @GetMapping("/books")
  public List<BookEntity> findAll() {
    Page<BookEntity> books = bookRepository.findAll(PageRequest.of(0, 100));
    return books.getContent();
  }

  /* @GetMapping("/book/{bookId}")
  public BookEntity getBook1(@PathVariable Long bookId) {
    return informationExtraction.getBook(String.valueOf(bookId));
  }*/

  @GetMapping("/book/{bookId}/textbook")
  public String getText(@PathVariable Long bookId) {
    return bookService.getText(bookId);
  }

  @GetMapping("/book/{bookId}/scores")
  public ReadabilityScoresEntity getScores(@PathVariable Long bookId) {
    return bookService.getScores(bookId);
  }
}
