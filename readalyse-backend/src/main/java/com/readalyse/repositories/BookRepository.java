package com.readalyse.repositories;

import com.readalyse.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
  Page<BookEntity> findAllByOrderByInsertTimeDesc(Pageable pageable);

  @Query(nativeQuery = true, value = "Select  from FAVORITE_BOOKS fb inner join BOOK book on fb.BOOK_ID=book.ID where fb.USER_ID=:id")
  Page<BookEntity> findFavorites(Long id, Pageable pageable);
}
