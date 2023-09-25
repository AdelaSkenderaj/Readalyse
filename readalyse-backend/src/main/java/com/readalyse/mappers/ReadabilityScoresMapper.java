package com.readalyse.mappers;

import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.model.ReadabilityScores;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReadabilityScoresMapper {

  ReadabilityScores entityToModel(ReadabilityScoresEntity readabilityScores);
}
