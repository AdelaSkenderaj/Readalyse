package com.readalyse.mappers;

import com.readalyse.entities.PersonEntity;
import com.readalyse.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonEntity modelToEntity(Person person);
}
