package com.readalyse.subject;

import com.readalyse.model.Subject;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

  SubjectEntity modelToEntity(SubjectModel subject);

  List<Subject> entitiesToModels(List<SubjectEntity> subjectEntityList);
}
