package com.readalyse.agentType;

import com.readalyse.model.AgentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentTypeMapper {

  AgentTypeEntity modelToEntity(AgentTypeModel agentType);

  AgentType entityToModel(AgentTypeEntity agentTypeEntity);
}
