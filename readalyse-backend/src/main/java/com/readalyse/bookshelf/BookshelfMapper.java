package com.readalyse.bookshelf;

import com.readalyse.model.Bookshelf;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
  BookshelfEntity modelToEntity(BookshelfModel bookshelf);

  List<Bookshelf> entitiesToModels(List<BookshelfEntity> bookshelfEntityList);
}
