package com.readalyse.mappers;

import com.readalyse.entities.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

  Status fromModel(String status);
}
