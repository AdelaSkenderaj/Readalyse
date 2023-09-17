package com.readalyse.mappers;

import com.readalyse.entities.LanguageEntity;
import com.readalyse.model.Language;
import com.readalyse.model.LanguageModel;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
  LanguageEntity modelToEntity(LanguageModel language);

  List<Language> entitiesToModels(List<LanguageEntity> languageEntityList);
}
