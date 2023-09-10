package com.readalyse.mappers;

import com.readalyse.entities.LanguageEntity;
import com.readalyse.model.Language;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
  LanguageEntity modelToEntity(Language language);
}
