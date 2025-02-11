package com.readalyse.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserModel entityToModel(UserEntity user);
}
