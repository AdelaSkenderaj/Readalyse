package com.readalyse.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readalyse.entities.AuditedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "PERSON")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonEntity extends AuditedEntity {

  @Id private Long id;
  private String name;
  private String alias;
  private Long birthdate;
  private Long deathdate;
  private String webpage;
}
