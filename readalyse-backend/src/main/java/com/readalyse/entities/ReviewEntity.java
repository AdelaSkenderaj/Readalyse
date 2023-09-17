package com.readalyse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "REVIEW")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends AuditedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "RATING")
  @Min(1)
  @Max(5)
  private Integer rating;

  @Column(name = "COMMENT")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "USER", referencedColumnName = "ID")
  private UserEntity user;
}
