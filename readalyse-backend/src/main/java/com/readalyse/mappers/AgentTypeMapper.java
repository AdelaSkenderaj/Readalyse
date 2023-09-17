package com.readalyse.mappers;

import com.readalyse.entities.AgentTypeEntity;
import com.readalyse.model.AgentType;
import com.readalyse.model.AgentTypeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentTypeMapper {

  AgentTypeEntity modelToEntity(AgentTypeModel agentType);

  AgentType entityToModel(AgentTypeEntity agentTypeEntity);
}
