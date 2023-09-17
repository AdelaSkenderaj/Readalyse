package com.readalyse.mappers;

import com.readalyse.entities.ResourceEntity;
import com.readalyse.model.Resource;
import com.readalyse.model.ResourceModel;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

  ResourceEntity modelToEntity(ResourceModel resource);

  List<ResourceEntity> modelsToEntities(List<ResourceModel> resourceList);

  List<Resource> entitiesToModels(List<ResourceEntity> resourceEntityList);
}
