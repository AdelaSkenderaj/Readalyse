package com.readalyse.agent;

import com.readalyse.agentType.AgentTypeEntity;
import com.readalyse.person.PersonEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
  Optional<AgentEntity> findByPersonAndType(PersonEntity person, AgentTypeEntity agentType);
}
