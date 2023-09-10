package com.readalyse.repositories;

import com.readalyse.entities.AgentEntity;
import com.readalyse.entities.AgentTypeEntity;
import com.readalyse.entities.PersonEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
  Optional<AgentEntity> findByPersonAndType(PersonEntity person, AgentTypeEntity agentType);
}
