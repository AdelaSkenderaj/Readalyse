package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
  private Long id;
  private String name;
  private String alias;
  private Long birthdate;
  private Long deathdate;
  private String webpage;
}
