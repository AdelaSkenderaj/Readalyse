package com.readalyse.repositories;

import com.readalyse.entities.AgentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentTypeRepository extends JpaRepository<AgentTypeEntity, Long> {
  Optional<AgentTypeEntity> findByName(String formatValue);
}
