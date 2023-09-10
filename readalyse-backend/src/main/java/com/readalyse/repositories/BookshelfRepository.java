package com.readalyse.repositories;

import com.readalyse.entities.BookshelfEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfRepository extends JpaRepository<BookshelfEntity, Long> {
  Optional<BookshelfEntity> findByName(String name);
}
