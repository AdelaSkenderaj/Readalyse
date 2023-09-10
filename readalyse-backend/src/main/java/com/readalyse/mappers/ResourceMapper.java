package com.readalyse.mappers;

import com.readalyse.entities.ResourceEntity;
import com.readalyse.model.Resource;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

  ResourceEntity modelToEntity(Resource resource);

  List<ResourceEntity> modelsToEntities(List<Resource> resourceList);
}
