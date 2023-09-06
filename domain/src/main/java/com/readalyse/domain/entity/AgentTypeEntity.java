package com.readalyse.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "AGENT_TYPE")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AgentTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
