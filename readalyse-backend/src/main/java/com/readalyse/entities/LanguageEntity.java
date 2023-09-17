package com.readalyse.entities;

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
}
