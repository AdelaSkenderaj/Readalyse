package com.readalyse.services;

import com.readalyse.entities.ReviewEntity;
import com.readalyse.mappers.ReviewMapper;
import com.readalyse.model.Pagination;
import com.readalyse.model.Review;
import com.readalyse.model.ReviewList;
import com.readalyse.repositories.ReviewRepository;
import com.readalyse.utility.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  public ReviewEntity createReview(Review review) {
    ReviewEntity reviewEntity =
        ReviewEntity.builder()
            .user(Utility.getUser())
            .comment(review.getComment())
            .rating(review.getRating())
            .bookId(review.getBookId())
            .build();
    return reviewRepository.save(reviewEntity);
  }

  public void deleteReview(Long reviewId) {
    reviewRepository.deleteById(reviewId);
  }

  public ReviewList getReviewsByBookId(Long bookId, Pagination pageRequest) {
    Page<ReviewEntity> reviews =
        reviewRepository.findAllByBookIdAndUserNot(
            bookId,
            Utility.getUser(),
            PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));

    Pagination pagination =
        new Pagination()
            .page(reviews.getNumber())
            .totalPages(reviews.getTotalPages())
            .totalElements(reviews.getTotalElements())
            .size(reviews.getSize());
    return new ReviewList()
        .reviews(reviewMapper.entitiesToModels(reviews.getContent()))
        .pagination(pagination);
  }

  public Review getReviewById(Long reviewId) {
    return reviewMapper.entityToModel(reviewRepository.findById(reviewId).orElse(null));
  }

  public Review getReviewOfUser(Long bookId) {
    return reviewMapper.entityToModel(
        reviewRepository.findByUserAndBookId(Utility.getUser(), bookId));
  }
}
