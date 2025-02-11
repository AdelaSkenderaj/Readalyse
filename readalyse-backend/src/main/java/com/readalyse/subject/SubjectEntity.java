package com.readalyse.subject;

import com.readalyse.entities.AuditedEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "SUBJECT")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectEntity extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public SubjectEntity(String name) {
    this.name = name;
  }
}
