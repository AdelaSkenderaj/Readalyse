package com.readalyse.mappers;

import com.readalyse.entities.SubjectEntity;
import com.readalyse.model.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  SubjectEntity modelToEntity(Subject subject);
}
