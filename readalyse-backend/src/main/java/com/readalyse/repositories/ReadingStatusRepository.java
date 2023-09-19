package com.readalyse.repositories;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadingStatusEntity;
import com.readalyse.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingStatusRepository extends JpaRepository<ReadingStatusEntity, Long> {
  List<ReadingStatusEntity> findByUser(UserEntity user);

  Optional<ReadingStatusEntity> findByBook(BookEntity book);
}
