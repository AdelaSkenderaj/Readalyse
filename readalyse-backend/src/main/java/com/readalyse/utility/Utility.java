package com.readalyse.utility;

import com.readalyse.entities.UserEntity;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class Utility {

  public UserEntity getUser() {
    return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
