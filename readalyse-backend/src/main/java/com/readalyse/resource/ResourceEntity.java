package com.readalyse.resource;

import com.readalyse.book.BookEntity;
import com.readalyse.entities.AuditedEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "RESOURCE")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResourceEntity extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "BOOK_ID", referencedColumnName = "id", nullable = false)
  private BookEntity bookEntity;

  private String url;
  private Long size;
  private String modified;
  private String type;
}
