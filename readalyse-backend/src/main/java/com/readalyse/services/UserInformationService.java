package com.readalyse.services;

import com.readalyse.entities.BookEntity;
import com.readalyse.entities.ReadingStatusEntity;
import com.readalyse.entities.UserEntity;
import com.readalyse.mappers.BookMapper;
import com.readalyse.mappers.ReadingStatusMapper;
import com.readalyse.mappers.StatusMapper;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.model.ReadingStatus;
import com.readalyse.repositories.BookRepository;
import com.readalyse.repositories.ReadingStatusRepository;
import com.readalyse.repositories.UserRepository;
import com.readalyse.utility.Utility;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

  public BookList getFavoriteBooksForUser(Pagination pageRequest) {
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

  public BookList getWantToReadBooksForUser(Pagination pageRequest) {
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

  public BookList getFinishedReadingBooksForUser(Pagination pageRequest) {
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

  public ReadingStatus updateStatus(Long bookId, String status) {
    UserEntity user = Utility.getUser();
    BookEntity book =
        bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    ReadingStatusEntity readingStatus = readingStatusRepository.findByBook(book).orElse(null);
    if (Objects.isNull(readingStatus)) {
      return readingStatusMapper.entityToModel(
          readingStatusRepository.save(
              ReadingStatusEntity.builder()
                  .user(user)
                  .book(book)
                  .status(statusMapper.fromModel(status))
                  .build()));
    } else {
      readingStatus.setStatus(statusMapper.fromModel(status));
      return readingStatusMapper.entityToModel(readingStatusRepository.save(readingStatus));
    }
  }

  public BookList updateFavorite(Long bookId) {
    UserEntity user = Utility.getUser();
    BookEntity book =
        bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    if (user.getFavoriteBooks().contains(book)) {
      user.getFavoriteBooks().remove(book);
      return new BookList()
          .books(bookMapper.entitiesToModels(userRepository.save(user).getFavoriteBooks()));
    }
    user.getFavoriteBooks().add(book);
    return new BookList()
        .books(bookMapper.entitiesToModels(userRepository.save(user).getFavoriteBooks()));
  }
}
