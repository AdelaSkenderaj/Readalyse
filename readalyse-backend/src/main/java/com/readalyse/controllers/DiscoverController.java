package com.readalyse.controllers;

import com.readalyse.api.DiscoverApi;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.services.DiscoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class DiscoverController implements DiscoverApi {

  private final DiscoverService discoverService;

  @Override
  public ResponseEntity<BookList> getRecommendedBooks(Pagination pagination) {
    return ResponseEntity.ok(discoverService.getRecommendedBooks(pagination));
  }

  @Override
  public ResponseEntity<BookList> getNewBooks(Pagination pagination) {
    return ResponseEntity.ok(discoverService.getNewBooks(pagination));
  }

  @Override
  public ResponseEntity<BookList> getTrendingBooks(Pagination pagination) {
    return ResponseEntity.ok(discoverService.getTrendingBooks(pagination));
  }

  @Override
  public ResponseEntity<BookList> getHighestRatedBooks(Pagination pagination) {
    return ResponseEntity.ok(discoverService.getHighestRatedBooks(pagination));
  }

  @Override
  public ResponseEntity<BookList> getBooksByCategory(String category, Pagination pagination) {
    return ResponseEntity.ok(discoverService.getBooksByCategory(category, pagination));
  }
}
