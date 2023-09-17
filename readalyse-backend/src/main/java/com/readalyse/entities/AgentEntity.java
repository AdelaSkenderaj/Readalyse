package com.readalyse.entities;

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
public class AgentEntity extends AuditedEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "PERSON", referencedColumnName = "ID")
  private PersonEntity person;

  @ManyToOne
  @JoinColumn(name = "TYPE", referencedColumnName = "ID")
  private AgentTypeEntity type;
}
