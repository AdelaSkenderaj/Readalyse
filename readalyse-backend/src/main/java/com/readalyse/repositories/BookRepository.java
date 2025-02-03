package com.readalyse.repositories;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.BookshelfEntity;
import com.readalyse.entities.SubjectEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
  Page<BookEntity> findAllByOrderByInsertTimeDesc(Pageable pageable);

  @Query(
      nativeQuery = true,
      value =
          "Select ID, TITLE, DESCRIPTION, DOWNLOADS, TYPE, INSERT_TIME, UPDATE_TIME from FAVORITE_BOOKS fb inner join BOOK book on fb.BOOK_ID=book.ID where fb.USER_ID=:id")
  Page<BookEntity> findFavorites(Long id, Pageable pageable);

  @Query(
      nativeQuery = true,
      value =
          "Select ID, TITLE, DESCRIPTION, DOWNLOADS, TYPE, INSERT_TIME, UPDATE_TIME from FAVORITE_BOOKS fb inner join BOOK book on fb.BOOK_ID=book.ID where fb.USER_ID=:id")
  List<BookEntity> findFavorites(Long id);

  @Query(
      nativeQuery = true,
      value = "Delete from FAVORITE_BOOKS where USER_ID=:userId and BOOK_ID=:bookId")
  void removeFromFavorites(Long userId, Long bookId);

  Page<BookEntity> findByBookshelvesContainingIgnoreCase(
      BookshelfEntity bookshelf, Pageable pageable);

  Page<BookEntity> findBySubjectsContainingIgnoreCase(SubjectEntity subject, PageRequest of);
}
