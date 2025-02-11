package com.readalyse.language;

import com.readalyse.model.Language;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
  LanguageEntity modelToEntity(LanguageModel language);

  List<Language> entitiesToModels(List<LanguageEntity> languageEntityList);
}
