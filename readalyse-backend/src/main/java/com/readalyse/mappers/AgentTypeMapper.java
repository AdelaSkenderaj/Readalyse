package com.readalyse.mappers;

import com.readalyse.entities.AgentTypeEntity;
import com.readalyse.model.AgentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentTypeMapper {

  AgentTypeEntity modelToEntity(AgentType agentType);
}
