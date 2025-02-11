package com.readalyse.bookshelf;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshelfRepository extends JpaRepository<BookshelfEntity, Long> {
  Optional<BookshelfEntity> findByName(String name);

  List<BookshelfEntity> findByNameIn(Set<String> bookshelves);
}
