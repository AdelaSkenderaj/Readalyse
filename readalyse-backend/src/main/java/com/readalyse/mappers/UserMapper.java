package com.readalyse.mappers;

import com.readalyse.entities.UserEntity;
import com.readalyse.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserModel entityToModel(UserEntity user);
}
