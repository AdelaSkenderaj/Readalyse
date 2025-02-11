package com.readalyse.book;

import com.readalyse.agent.AgentMapper;
import com.readalyse.model.Book;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AgentMapper.class)
public interface BookMapper {

  @Mapping(target = "readabilityScores", ignore = true)
  @Mapping(target = "agents", ignore = true)
  @Mapping(target = "bookshelves", ignore = true)
  @Mapping(target = "languages", ignore = true)
  @Mapping(target = "resources", ignore = true)
  @Mapping(target = "subjects", ignore = true)
  BookEntity modelToEntity(BookModel book);

  List<Book> entitiesToModels(List<BookEntity> bookEntityList);

  Book entityToModel(BookEntity book);
}
