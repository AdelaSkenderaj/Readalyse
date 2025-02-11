package com.readalyse.person;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonEntity modelToEntity(PersonModel person);
}
