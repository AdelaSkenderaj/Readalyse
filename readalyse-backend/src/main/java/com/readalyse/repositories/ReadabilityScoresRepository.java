package com.readalyse.repositories;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadabilityScoresEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadabilityScoresRepository extends JpaRepository<ReadabilityScoresEntity, Long> {

  @Query(
      "SELECT book from BookEntity book where book.id IN (SELECT rse.bookId FROM ReadabilityScoresEntity rse where (rse.fleschKincaidGradeLevel BETWEEN :fleschKincaidGradeLevel AND :fleschKincaidGradeLevel+1) "
          + "or (rse.fleschReadingEase between :fleschReadingEase AND :fleschReadingEase + 1) "
          + "or (rse.colemanLiauIndex between :colemanLiauIndex and :colemanLiauIndex + 1) "
          + "or (rse.smogIndex between :smogIndex and :smogIndex + 1) "
          + "or (rse.automatedReadabilityIndex between  :automatedReadabilityIndex and :automatedReadabilityIndex + 1) "
          + "or (rse.forcastIndex between :forcastIndex and :forcastIndex + 1) "
          + "or (rse.lixIndex between :lixIndex and :lixIndex + 1) "
          + "or (rse.rixIndex between :rixIndex and :rixIndex + 1))")
  List<BookEntity> filterByScores(
      Double fleschKincaidGradeLevel,
      Double fleschReadingEase,
      Double colemanLiauIndex,
      Double smogIndex,
      Double automatedReadabilityIndex,
      Double forcastIndex,
      Double lixIndex,
      Double rixIndex);
}
