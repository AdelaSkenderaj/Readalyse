package com.readalyse.services;

import com.readalyse.book.BookEntity;
import com.readalyse.book.BookMapper;
import com.readalyse.book.BookRepository;
import com.readalyse.entities.ReadingStatusEntity;
import com.readalyse.enums.Status;
import com.readalyse.enums.mapper.StatusMapper;
import com.readalyse.mappers.ReadingStatusMapper;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.model.ReadingStatus;
import com.readalyse.repositories.ReadingStatusRepository;
import com.readalyse.user.UserEntity;
import com.readalyse.user.UserRepository;
import com.readalyse.utility.Utility;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInformationService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final ReadingStatusRepository readingStatusRepository;
  private final StatusMapper statusMapper;
  private final ReadingStatusMapper readingStatusMapper;

  private final UserRepository userRepository;

  public BookList getCurrentlyReadingBooksForUser(Pagination pageRequest) {
    UserEntity user = Utility.getUser();
    List<BookEntity> books =
        readingStatusRepository
            .findByStatusAndUser(
                Status.CURRENTLY_READING.toString(),
                user,
                PageRequest.of(pageRequest.getPage(), pageRequest.getSize()))
            .stream()
            .map(ReadingStatusEntity::getBook)
            .collect(Collectors.toList());
    Pagination pagination =
        new Pagination()
            .page(pageRequest.getPage())
            .totalPages(books.size() / pageRequest.getSize())
            .totalElements((long) books.size())
            .size(books.size());
    return new BookList().books(bookMapper.entitiesToModels(books)).pagination(pagination);
  }

  public BookList getFavoriteBooksForUser(Pagination pageRequest) {
    UserEntity user = Utility.getUser();
    List<BookEntity> books =
        bookRepository
            .findFavorites(
                user.getId(), PageRequest.of(pageRequest.getPage(), pageRequest.getSize()))
            .getContent();
    Pagination pagination =
        new Pagination()
            .page(pageRequest.getPage())
            .totalPages(books.size() / pageRequest.getSize())
            .totalElements((long) books.size())
            .size(books.size());
    return new BookList().books(bookMapper.entitiesToModels(books)).pagination(pagination);
  }

  public BookList getWantToReadBooksForUser(Pagination pageRequest) {
    UserEntity user = Utility.getUser();
    List<BookEntity> books =
        readingStatusRepository
            .findByStatusAndUser(
                Status.WANT_TO_READ.toString(),
                user,
                PageRequest.of(pageRequest.getPage(), pageRequest.getSize()))
            .stream()
            .map(ReadingStatusEntity::getBook)
            .collect(Collectors.toList());
    Pagination pagination =
        new Pagination()
            .page(pageRequest.getPage())
            .totalPages(books.size() / pageRequest.getSize())
            .totalElements((long) books.size())
            .size(books.size());
    return new BookList().books(bookMapper.entitiesToModels(books)).pagination(pagination);
  }

  public BookList getFinishedReadingBooksForUser(Pagination pageRequest) {
    UserEntity user = Utility.getUser();
    List<BookEntity> books =
        readingStatusRepository
            .findByStatusAndUser(
                Status.READ.toString(),
                user,
                PageRequest.of(pageRequest.getPage(), pageRequest.getSize()))
            .stream()
            .map(ReadingStatusEntity::getBook)
            .collect(Collectors.toList());
    Pagination pagination =
        new Pagination()
            .page(pageRequest.getPage())
            .totalPages(books.size() / pageRequest.getSize())
            .totalElements((long) books.size())
            .size(books.size());
    return new BookList().books(bookMapper.entitiesToModels(books)).pagination(pagination);
  }

  public ReadingStatus updateStatus(Long bookId, String status) {
    UserEntity user = Utility.getUser();
    BookEntity book =
        bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    ReadingStatusEntity readingStatus = readingStatusRepository.findByBook(book).orElse(null);
    if (Objects.isNull(readingStatus)) {
      return readingStatusMapper.entityToModel(
          readingStatusRepository.save(
              ReadingStatusEntity.builder().user(user).book(book).status(status).build()));
    } else {
      readingStatus.setStatus(status);
      return readingStatusMapper.entityToModel(readingStatusRepository.save(readingStatus));
    }
  }

  public BookList updateFavorite(Long bookId) {
    UserEntity user = Utility.getUser();
    BookEntity book =
        bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    List<BookEntity> favoriteBooks = bookRepository.findFavorites(user.getId());
    if (favoriteBooks.contains(book)) {
      bookRepository.removeFromFavorites(user.getId(), bookId);
      return new BookList()
          .books(bookMapper.entitiesToModels(bookRepository.findFavorites(user.getId())));
    }
    user.getFavoriteBooks().add(book);
    return new BookList()
        .books(bookMapper.entitiesToModels(userRepository.save(user).getFavoriteBooks()));
  }

  public Boolean isFavoriteBook(Long bookId) {
    UserEntity user = Utility.getUser();
    BookEntity book =
        bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

    List<BookEntity> favoriteBooks = bookRepository.findFavorites(user.getId());
    return favoriteBooks.contains(book);
  }
}
