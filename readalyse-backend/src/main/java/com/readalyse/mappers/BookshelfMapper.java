package com.readalyse.mappers;

import com.readalyse.entities.BookshelfEntity;
import com.readalyse.model.Bookshelf;
import com.readalyse.model.BookshelfModel;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
  BookshelfEntity modelToEntity(BookshelfModel bookshelf);

  List<Bookshelf> entitiesToModels(List<BookshelfEntity> bookshelfEntityList);
}
