package com.readalyse.mappers;

import com.readalyse.entities.AgentEntity;
import com.readalyse.model.Agent;
import com.readalyse.model.AgentModel;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgentMapper {

  AgentEntity modelToEntity(AgentModel agent);

  @Mapping(target = "agentType", source = "type")
  List<Agent> entitiesToModels(List<AgentEntity> agentEntities);

  Agent entityToModel(AgentEntity agentEntity);
}
