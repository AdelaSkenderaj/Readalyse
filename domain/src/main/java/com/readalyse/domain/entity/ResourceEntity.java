package com.readalyse.domain.entity;

import java.time.LocalDateTime;
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
public class ResourceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;
  private Long size;
  private LocalDateTime modified;
  private String type;
}
