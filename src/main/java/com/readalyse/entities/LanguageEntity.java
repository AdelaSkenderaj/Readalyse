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
public class LanguageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String language;
}
