package com.readalyse.book;

import com.readalyse.agent.AgentEntity;
import com.readalyse.bookshelf.BookshelfEntity;
import com.readalyse.entities.AuditedEntity;
import com.readalyse.entities.ReadabilityScoresEntity;
import com.readalyse.language.LanguageEntity;
import com.readalyse.resource.ResourceEntity;
import com.readalyse.subject.SubjectEntity;
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
public class BookEntity extends AuditedEntity {

  @Id private Long id;
  private String title;
  private String description;
  private String summary;
  private Long downloads;
  private String type;

  @OneToOne(cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn
  private ReadabilityScoresEntity readabilityScores;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "BOOK_AGENT",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "AGENT"))
  private List<AgentEntity> agents;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "BOOK_BOOKSHELF",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "BOOKSHELF"))
  private List<BookshelfEntity> bookshelves;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "BOOK_LANGUAGE",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "LANGUAGE"))
  private List<LanguageEntity> languages;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookEntity", orphanRemoval = true)
  private List<ResourceEntity> resources;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      name = "BOOK_SUBJECT",
      joinColumns = @JoinColumn(name = "BOOK"),
      inverseJoinColumns = @JoinColumn(name = "SUBJECT"))
  private List<SubjectEntity> subjects;

  public void setResources(List<ResourceEntity> resources) {
    resources.forEach(resourceEntity -> resourceEntity.setBookEntity(this));
    this.resources = resources;
  }
}
