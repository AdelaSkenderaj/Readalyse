package com.readalyse.mappers;

import com.readalyse.entities.SubjectEntity;
import com.readalyse.model.SubjectModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  SubjectEntity modelToEntity(SubjectModel subject);
}
