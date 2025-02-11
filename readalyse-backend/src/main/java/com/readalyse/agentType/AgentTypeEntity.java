package com.readalyse.agentType;

import com.readalyse.entities.AuditedEntity;
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
public class AgentTypeEntity extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public AgentTypeEntity(String name) {
    this.name = name;
  }
}
