package com.readalyse.resource;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

  @Mapping(target = "bookEntity", ignore = true)
  ResourceEntity modelToEntity(ResourceModel resource);

  List<ResourceEntity> modelsToEntities(List<ResourceModel> resourceList);

  List<ResourceModel> entitiesToModels(List<ResourceEntity> resourceEntityList);
}
