package com.readalyse.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "READING_STATUS")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReadingStatusEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
  private BookEntity book;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  private UserEntity user;

  @Column(name = "STATUS")
  private String status;
}
