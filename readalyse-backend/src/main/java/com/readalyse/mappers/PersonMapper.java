package com.readalyse.mappers;

import com.readalyse.entities.PersonEntity;
import com.readalyse.model.PersonModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonEntity modelToEntity(PersonModel person);
}
