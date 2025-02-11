package com.readalyse.agent;

import com.readalyse.agentType.AgentTypeEntity;
import com.readalyse.entities.AuditedEntity;
import com.readalyse.person.PersonEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "AGENT")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AgentEntity extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "PERSON", referencedColumnName = "ID")
  private PersonEntity person;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "TYPE", referencedColumnName = "ID")
  private AgentTypeEntity type;

  public AgentEntity(PersonEntity person, AgentTypeEntity type) {
    this.person = person;
    this.type = type;
  }
}
