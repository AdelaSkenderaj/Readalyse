package com.readalyse.mappers;

import com.readalyse.entities.AgentEntity;
import com.readalyse.model.Agent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentMapper {

  AgentEntity modelToEntity(Agent agent);
}
