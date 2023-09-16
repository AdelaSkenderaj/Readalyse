package com.readalyse.services;

import com.readalyse.entities.BookEntity;
import com.readalyse.mappers.BookMapper;
import com.readalyse.model.BookList;
import com.readalyse.model.Pagination;
import com.readalyse.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscoverService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookList getRecommendedBooks(Pagination pageRequest) {
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
