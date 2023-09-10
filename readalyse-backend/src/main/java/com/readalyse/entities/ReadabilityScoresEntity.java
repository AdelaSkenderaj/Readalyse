package com.readalyse.entities;

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

  @Id
  @Column(name = "BOOK_ID")
  private Long bookId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "BOOK_ID")
  private BookEntity book;

  @Column(name = "FLESCH_KINCAID_GRADE_LEVEL")
  private Double fleschKincaidGradeLevel;
  @Column(name = "FLESCH_READING_EASE")
  private Double fleschReadingEase;
  @Column(name = "COLEMAN_LIAU_INDEX")
  private Double colemanLiauIndex;
  @Column(name = "SMOG_INDEX")
  private Double smogIndex;
  @Column(name = "AUTOMATED_READABILITY_INDEX")
  private Double automatedReadabilityIndex;
  @Column(name = "FORCAST_INDEX")
  private Double forcastIndex;
//  private Double powersSumnerKearl;
  @Column(name = "LIX_INDEX")
  private Double lixIndex;
  @Column(name = "RIX_INDEX")
  private Double rixIndex;
}
