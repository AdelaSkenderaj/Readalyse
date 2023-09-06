package com.readalyse.repositories;

import com.readalyse.domain.entity.AgentEntity;
import com.readalyse.domain.entity.AgentTypeEntity;
import com.readalyse.domain.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
  AgentEntity findByPersonAndType(PersonEntity person, AgentTypeEntity agentType);
}
