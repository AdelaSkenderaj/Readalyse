package com.readalyse.entities;

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
public class SubjectEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
