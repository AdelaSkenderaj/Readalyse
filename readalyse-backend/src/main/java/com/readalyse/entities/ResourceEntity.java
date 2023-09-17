package com.readalyse.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

  private String url;
  private Long size;
  private LocalDateTime modified;
  private String type;
}
