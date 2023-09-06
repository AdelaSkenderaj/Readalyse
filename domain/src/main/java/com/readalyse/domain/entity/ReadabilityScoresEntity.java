package com.readalyse.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "READABILITY_SCORES")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReadabilityScoresEntity {

  @Id private Long bookId;

  private Double fleschKincaidGradeLevel;
  private Double fleschReadingEase;
  private Double colemanLiauIndex;
  private Double smogIndex;
  private Double automatedReadabilityIndex;
  private Double forcastIndex;
  private Double powersSumnerKearl;
  private Double lixIndex;
  private Double rixIndex;
}
