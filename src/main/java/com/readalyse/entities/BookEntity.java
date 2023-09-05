package com.readalyse.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Table(name = "BOOK")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity {

  @Id private Long id;
  private String title;
  private String description;
  private Long downloads;

  @OneToOne(cascade = CascadeType.MERGE)
  private ReadabilityScoresEntity readabilityScores;

  @ManyToMany
  @JoinTable(
      name = "BOOK_AGENT",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "AGENT"))
  private List<AgentEntity> agents;

  @ManyToMany
  @JoinTable(
      name = "BOOK_BOOKSHELF",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "BOOKSHELF"))
  private List<BookshelfEntity> bookshelves;

  @ManyToMany
  @JoinTable(
      name = "BOOK_LANGUAGE",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "LANGUAGE"))
  private List<LanguageEntity> languages;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "BOOK_RESOURCE",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "RESOURCE"))
  private List<ResourceEntity> resources;

  @ManyToMany
  @JoinTable(
      name = "BOOK_SUBJECT",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "SUBJECT"))
  private List<SubjectEntity> subjects;
}
