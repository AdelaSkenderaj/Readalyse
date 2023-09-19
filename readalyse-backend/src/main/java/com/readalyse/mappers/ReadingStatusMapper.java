package com.readalyse.mappers;

import com.readalyse.entities.ReadingStatusEntity;
import com.readalyse.model.ReadingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadingStatusMapper {

  @Mapping(target = "book", source = "book.id")
  @Mapping(target = "user", source = "user.id")
  ReadingStatus entityToModel(ReadingStatusEntity readingStatus);
}
