package com.readalyse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "USERNAME")
  private String username;

  @Column(name = "AGE")
  private Integer age;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "PHOTO")
  private String photo;
}
