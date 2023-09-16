package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentModel {
  private Long id;
  private PersonModel person;
  private AgentTypeModel type;
}
