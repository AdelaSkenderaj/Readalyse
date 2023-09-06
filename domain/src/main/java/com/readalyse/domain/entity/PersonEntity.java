package com.readalyse.domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "PERSON")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonEntity {

  @Id private Long id;
  private String name;
  private String alias;
  private Long birthdate;
  private Long deathdate;
  private String webpage;
}
