package com.readalyse.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "BOOKSHELF")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookshelfEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
