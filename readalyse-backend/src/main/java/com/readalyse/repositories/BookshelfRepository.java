package com.readalyse.repositories;

import com.readalyse.domain.entity.BookshelfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfRepository extends JpaRepository<BookshelfEntity, Long> {
  BookshelfEntity findByName(String name);
}
