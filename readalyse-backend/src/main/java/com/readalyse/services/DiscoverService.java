package com.readalyse.services;

import com.readalyse.entities.*;
import com.readalyse.mappers.BookMapper;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.repositories.*;
import com.readalyse.utility.Utility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
  private final BookshelfRepository bookshelfRepository;

  private final ReadingStatusRepository readingStatusRepository;

  private final ReadabilityScoresRepository readabilityScoresRepository;

  public BookList getRecommendedBooks(Pagination pageRequest) {

    UserEntity user = Utility.getUser();
    List<BookEntity> preferredBooks = new ArrayList<>();
    List<BookEntity> readBooks = new ArrayList<>();
    readingStatusRepository
        .findByUser(user)
        .forEach(
            (readingStatusEntity -> {
              preferredBooks.add(readingStatusEntity.getBook());
              if (Objects.equals(readingStatusEntity.getStatus(), "READ")) {
                readBooks.add(readingStatusEntity.getBook());
              }
            }));
    List<BookEntity> favoriteBooks =
        bookRepository
            .findFavorites(
                user.getId(), PageRequest.of(pageRequest.getPage(), pageRequest.getSize()))
            .getContent();
    preferredBooks.addAll(favoriteBooks);
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
    preferredBooks.forEach(booksInRangeOfScores::remove);
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

    List<ReadabilityScoresEntity> readabilityScoresEntities = new ArrayList<>();
    readBooks.forEach(
        book ->
            readabilityScoresRepository
                .findByBookId(book.getId())
                .ifPresent(readabilityScoresEntities::add));

    for (ReadabilityScoresEntity readabilityScore : readabilityScoresEntities) {
      readabilityScores.setFleschKincaidGradeLevel(
          readabilityScores.getFleschKincaidGradeLevel()
              + readabilityScore.getFleschKincaidGradeLevel());
      readabilityScores.setFleschReadingEase(
          readabilityScores.getFleschReadingEase() + readabilityScore.getFleschReadingEase());
      readabilityScores.setColemanLiauIndex(
          readabilityScores.getColemanLiauIndex() + readabilityScore.getColemanLiauIndex());
      readabilityScores.setSmogIndex(
          readabilityScores.getSmogIndex() + readabilityScore.getSmogIndex());
      readabilityScores.setAutomatedReadabilityIndex(
          readabilityScores.getAutomatedReadabilityIndex()
              + readabilityScore.getAutomatedReadabilityIndex());
      readabilityScores.setLixIndex(
          readabilityScores.getLixIndex() + readabilityScore.getLixIndex());
      readabilityScores.setRixIndex(
          readabilityScores.getRixIndex() + readabilityScore.getRixIndex());
    }

    readabilityScores.setFleschKincaidGradeLevel(
        readabilityScores.getFleschKincaidGradeLevel() / readabilityScoresEntities.size());
    readabilityScores.setFleschReadingEase(
        readabilityScores.getFleschReadingEase() / readabilityScoresEntities.size());
    readabilityScores.setColemanLiauIndex(
        readabilityScores.getColemanLiauIndex() / readabilityScoresEntities.size());
    readabilityScores.setSmogIndex(
        readabilityScores.getSmogIndex() / readabilityScoresEntities.size());
    readabilityScores.setAutomatedReadabilityIndex(
        readabilityScores.getAutomatedReadabilityIndex() / readabilityScoresEntities.size());
    readabilityScores.setLixIndex(
        readabilityScores.getLixIndex() / readabilityScoresEntities.size());
    readabilityScores.setRixIndex(
        readabilityScores.getRixIndex() / readabilityScoresEntities.size());

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

  public BookList getBooksByCategory(String category, Pagination pageRequest) {
    BookshelfEntity bookshelf =
        bookshelfRepository.findByName(category).orElseThrow(() -> new RuntimeException());
    Page<BookEntity> books =
        bookRepository.findByBookshelvesContainingIgnoreCase(
            bookshelf, PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
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
