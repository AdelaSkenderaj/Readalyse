package com.readalyse.controllers;

import com.readalyse.entities.BookEntity;
import com.readalyse.repositories.BookRepository;
import com.readalyse.services.BookService;
import com.readalyse.utility.FileRetrieval;
import com.readalyse.utility.InformationExtraction;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

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
  public void retrieveFiles() throws IOException, ParserConfigurationException, SAXException {
    fileRetrieval.getRdfData();
  }

  @GetMapping("/books")
  public List<BookEntity> findAll() {
    return bookRepository.findAll();
  }

  @GetMapping("/book/{bookId}")
  public BookEntity getBook1(@PathVariable Long bookId) {
    informationExtraction.getBook(String.valueOf(bookId));
    informationExtraction.getBook(String.valueOf(1L));
    return informationExtraction.getBook(String.valueOf(bookId));
  }

  @GetMapping("/book/{bookId}/textbook")
  public String getText(@PathVariable Long bookId) {
    return bookService.getText(bookId);
  }
}
