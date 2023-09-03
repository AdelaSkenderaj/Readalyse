package com.readalyse.repositories;

import com.readalyse.entities.AgentEntity;
import com.readalyse.entities.AgentTypeEntity;
import com.readalyse.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
  AgentEntity findByPersonAndType(PersonEntity person, AgentTypeEntity agentType);
}
