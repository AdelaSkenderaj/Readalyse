package com.readalyse.entities;

import com.readalyse.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Table(name = "REVIEW")
@Entity
@Getter
@Setter
@Builder
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

  @Column(name = "BOOK_ID")
  private Long bookId;
}
