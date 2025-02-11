package com.readalyse.agent;

import com.readalyse.model.Agent;
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
