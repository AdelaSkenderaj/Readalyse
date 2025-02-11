package com.readalyse.language;

import com.readalyse.entities.AuditedEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "LANGUAGE")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LanguageEntity extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String language;

  public LanguageEntity(String language) {
    this.language = language;
  }
}
