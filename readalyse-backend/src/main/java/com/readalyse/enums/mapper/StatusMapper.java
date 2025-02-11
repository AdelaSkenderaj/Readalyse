package com.readalyse.enums.mapper;

import com.readalyse.enums.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

  Status fromModel(String status);
}
