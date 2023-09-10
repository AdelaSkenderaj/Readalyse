package com.readalyse.mappers;

import com.readalyse.entities.BookEntity;
import com.readalyse.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target = "readabilityScores", ignore = true)
  @Mapping(target = "agents", ignore = true)
  @Mapping(target = "bookshelves", ignore = true)
  @Mapping(target = "languages", ignore = true)
  @Mapping(target = "resources", ignore = true)
  @Mapping(target = "subjects", ignore = true)
  BookEntity modelToEntity(Book book);
}
