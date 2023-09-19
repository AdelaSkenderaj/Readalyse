package com.readalyse.services;

import com.readalyse.entities.*;
import com.readalyse.mappers.BookMapper;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.repositories.BookRepository;
import com.readalyse.repositories.ReadabilityScoresRepository;
import com.readalyse.repositories.ReadingStatusRepository;
import com.readalyse.repositories.UserRepository;
import com.readalyse.utility.Utility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscoverService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  private final UserRepository userRepository;

  private final ReadingStatusRepository readingStatusRepository;

  private final ReadabilityScoresRepository readabilityScoresRepository;

  // TODO Change logic for each discover method
  public BookList getRecommendedBooks(Pagination pageRequest) {

    UserEntity user = Utility.getUser();
    List<BookEntity> preferredBooks = new ArrayList<>();
    List<BookEntity> readBooks = new ArrayList<>();
    readingStatusRepository
        .findByUser(user)
        .forEach(
            (readingStatusEntity -> {
              preferredBooks.add(readingStatusEntity.getBook());
              if (readingStatusEntity.getStatus() == "READ") {
                readBooks.add(readingStatusEntity.getBook());
              }
            }));
    List<BookshelfEntity> preferredBookshelves =
        preferredBooks.stream()
            .map(BookEntity::getBookshelves)
            .flatMap(Collection::stream)
            .distinct()
            .toList();

    ReadabilityScoresEntity scores = getAverageReadabilityScoreOfReadBooks(readBooks);
    List<BookEntity> booksInRangeOfScores =
        readabilityScoresRepository.filterByScores(
            scores.getFleschKincaidGradeLevel(),
            scores.getFleschReadingEase(),
            scores.getColemanLiauIndex(),
            scores.getSmogIndex(),
            scores.getAutomatedReadabilityIndex(),
            scores.getForcastIndex(),
            scores.getLixIndex(),
            scores.getRixIndex());
    List<BookEntity> sortedByPreferredBookshelf =
        booksInRangeOfScores.stream()
            .sorted(
                (book1, book2) -> {
                  long count1 =
                      book1.getBookshelves().stream()
                          .filter(preferredBookshelves::contains)
                          .count();

                  long count2 =
                      book2.getBookshelves().stream()
                          .filter(preferredBookshelves::contains)
                          .count();
                  return Long.compare(count2, count1);
                })
            .skip((long) (pageRequest.getPage()) * pageRequest.getSize())
            .limit(pageRequest.getSize())
            .toList();
    Pagination pagination =
        new Pagination()
            .page(pageRequest.getPage())
            .totalPages(
                pageRequest.getPage() == 0
                    ? booksInRangeOfScores.size()
                    : booksInRangeOfScores.size() / pageRequest.getPage())
            .totalElements((long) booksInRangeOfScores.size())
            .size(pageRequest.getSize());
    return new BookList()
        .books(bookMapper.entitiesToModels(sortedByPreferredBookshelf))
        .pagination(pagination);
  }

  private ReadabilityScoresEntity getAverageReadabilityScoreOfReadBooks(
      List<BookEntity> readBooks) {
    ReadabilityScoresEntity readabilityScores =
        new ReadabilityScoresEntity(null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    int numberOfBooks = readBooks.size();

    if (numberOfBooks > 0) {
      readabilityScores.setFleschKincaidGradeLevel(
          readBooks.stream()
                  .mapToDouble(book -> book.getReadabilityScores().getFleschKincaidGradeLevel())
                  .sum()
              / numberOfBooks);

      readabilityScores.setFleschReadingEase(
          readBooks.stream()
                  .mapToDouble(book -> book.getReadabilityScores().getFleschReadingEase())
                  .sum()
              / numberOfBooks);

      readabilityScores.setColemanLiauIndex(
          readBooks.stream()
                  .mapToDouble(book -> book.getReadabilityScores().getColemanLiauIndex())
                  .sum()
              / numberOfBooks);

      readabilityScores.setSmogIndex(
          readBooks.stream().mapToDouble(book -> book.getReadabilityScores().getSmogIndex()).sum()
              / numberOfBooks);

      readabilityScores.setAutomatedReadabilityIndex(
          readBooks.stream()
                  .mapToDouble(book -> book.getReadabilityScores().getAutomatedReadabilityIndex())
                  .sum()
              / numberOfBooks);

      readabilityScores.setForcastIndex(
          readBooks.stream()
                  .mapToDouble(book -> book.getReadabilityScores().getForcastIndex())
                  .sum()
              / numberOfBooks);

      readabilityScores.setLixIndex(
          readBooks.stream().mapToDouble(book -> book.getReadabilityScores().getLixIndex()).sum()
              / numberOfBooks);

      readabilityScores.setRixIndex(
          readBooks.stream().mapToDouble(book -> book.getReadabilityScores().getRixIndex()).sum()
              / numberOfBooks);
    }
    return readabilityScores;
  }

  public BookList getNewBooks(Pagination pageRequest) {
    Page<BookEntity> books =
        bookRepository.findAllByOrderByInsertTimeDesc(
            PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
    Pagination pagination =
        new Pagination()
            .page(books.getNumber())
            .totalPages(books.getTotalPages())
            .totalElements(books.getTotalElements())
            .size(books.getSize());
    return new BookList()
        .books(bookMapper.entitiesToModels(books.getContent()))
        .pagination(pagination);
  }

  public BookList getTrendingBooks(Pagination pageRequest) {
    Page<BookEntity> books =
        bookRepository.findAll(PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
    Pagination pagination =
        new Pagination()
            .page(books.getNumber())
            .totalPages(books.getTotalPages())
            .totalElements(books.getTotalElements())
            .size(books.getSize());
    return new BookList()
        .books(bookMapper.entitiesToModels(books.getContent()))
        .pagination(pagination);
  }

  public BookList getHighestRatedBooks(Pagination pageRequest) {
    Page<BookEntity> books =
        bookRepository.findAll(PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
    Pagination pagination =
        new Pagination()
            .page(books.getNumber())
            .totalPages(books.getTotalPages())
            .totalElements(books.getTotalElements())
            .size(books.getSize());
    return new BookList()
        .books(bookMapper.entitiesToModels(books.getContent()))
        .pagination(pagination);
  }
}
