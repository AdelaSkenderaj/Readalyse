package com.readalyse.repositories;

import com.readalyse.entities.ReviewEntity;
import com.readalyse.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
  Page<ReviewEntity> findAllByBookIdAndUserNot(Long bookId, UserEntity user, PageRequest of);

  ReviewEntity findByUserAndBookId(UserEntity user, Long bookId);
}
