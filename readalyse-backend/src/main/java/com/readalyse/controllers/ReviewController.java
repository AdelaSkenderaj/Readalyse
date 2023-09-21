package com.readalyse.controllers;

import com.readalyse.api.ReviewApi;
import com.readalyse.mappers.ReviewMapper;
import com.readalyse.model.Pagination;
import com.readalyse.model.Review;
import com.readalyse.model.ReviewList;
import com.readalyse.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

  private final ReviewService reviewService;
  private final ReviewMapper reviewMapper;

  @Override
  public ResponseEntity<Review> createReview(Review review) {
    return ResponseEntity.ok(reviewMapper.entityToModel(reviewService.createReview(review)));
  }

  @Override
  public ResponseEntity<Void> deleteReview(Long reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<ReviewList> getReviewsByBookId(Long bookId, Pagination pagination) {
    return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId, pagination));
  }

  @Override
  public ResponseEntity<Review> getReviewById(Long reviewId) {
    return ResponseEntity.ok(reviewService.getReviewById(reviewId));
  }

  @Override
  public ResponseEntity<Review> getReviewOfUser(Long bookId) {
    return ResponseEntity.ok(reviewService.getReviewOfUser(bookId));
  }
}
