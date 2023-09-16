package com.readalyse.mappers;

import com.readalyse.entities.AgentEntity;
import com.readalyse.model.AgentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentMapper {

  AgentEntity modelToEntity(AgentModel agent);
}
