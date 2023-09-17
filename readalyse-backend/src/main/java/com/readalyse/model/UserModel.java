package com.readalyse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
  private Long id;
  private String firstName;
  private String lastName;
  private String username;
  private Integer age;
  private String email;
  private String password;
  private String photo;
  private RoleModel role;
}
