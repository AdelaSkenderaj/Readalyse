package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Agent {
  private Long id;
  private Person person;
  private AgentType type;
}
