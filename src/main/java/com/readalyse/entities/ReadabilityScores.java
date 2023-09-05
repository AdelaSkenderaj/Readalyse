package com.readalyse.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "READABILITY_SCORES")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReadabilityScores {

  @Id private Long bookId;

  private Double fleschKincaidGradeLevel;
  private Double fleschReadingEase;
  private Double colemanLiauIndex;
  private Double smogIndex;
  private Double automatedReadabilityIndex;
  private Double forcastIndex;
  //  private Double powersSumnerKearl;
  private Double lixIndex;
  private Double rixIndex;
}
