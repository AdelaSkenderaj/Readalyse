package com.readalyse.agentType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentTypeModel {
  private Long id;
  private String name;
}
