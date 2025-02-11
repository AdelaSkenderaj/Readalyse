package com.readalyse.agent;

import com.readalyse.agentType.AgentTypeModel;
import com.readalyse.person.PersonModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentModel {
  private Long id;
  private PersonModel person;
  private AgentTypeModel type;
}
