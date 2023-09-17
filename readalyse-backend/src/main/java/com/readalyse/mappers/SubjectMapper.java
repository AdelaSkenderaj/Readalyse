package com.readalyse.mappers;

import com.readalyse.entities.SubjectEntity;
import com.readalyse.model.Subject;
import com.readalyse.model.SubjectModel;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  SubjectEntity modelToEntity(SubjectModel subject);

  List<Subject> entitiesToModels(List<SubjectEntity> subjectEntityList);
}
