package com.readalyse.mappers;

import com.readalyse.entities.BookshelfEntity;
import com.readalyse.model.Bookshelf;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
  BookshelfEntity modelToEntity(Bookshelf bookshelf);
}
