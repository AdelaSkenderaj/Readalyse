package com.readalyse.controllers;

import com.readalyse.api.LibraryApi;
import com.readalyse.model.BookList;
import com.readalyse.model.IsFavorite;
import com.readalyse.model.Pagination;
import com.readalyse.model.ReadingStatus;
import com.readalyse.services.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController implements LibraryApi {

  private final UserInformationService userInformationService;

  @Override
  public ResponseEntity<BookList> getCurrentlyReadingBooksForUser(Pagination pagination) {
    return ResponseEntity.ok(userInformationService.getCurrentlyReadingBooksForUser(pagination));
  }

  @Override
  public ResponseEntity<BookList> getFavoriteBooksForUser(Pagination pagination) {
    return ResponseEntity.ok(userInformationService.getFavoriteBooksForUser(pagination));
  }

  @Override
  public ResponseEntity<BookList> getWantToReadBooksForUser(Pagination pagination) {
    return ResponseEntity.ok(userInformationService.getWantToReadBooksForUser(pagination));
  }

  @Override
  public ResponseEntity<BookList> getFinishedReadingBooksForUser(Pagination pagination) {
    return ResponseEntity.ok(userInformationService.getFinishedReadingBooksForUser(pagination));
  }

  @Override
  public ResponseEntity<ReadingStatus> updateStatus(Long bookId, String status) {
    return ResponseEntity.ok(userInformationService.updateStatus(bookId, status));
  }

  @Override
  public ResponseEntity<BookList> updateFavorite(Long bookId) {
    return ResponseEntity.ok(userInformationService.updateFavorite(bookId));
  }

  @Override
  public ResponseEntity<IsFavorite> checkIfFavorite(Long bookId) {
    return ResponseEntity.ok(
        new IsFavorite().favorite(userInformationService.isFavoriteBook(bookId)));
  }
}
