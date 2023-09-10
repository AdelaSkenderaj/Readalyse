package com.readalyse.repositories;

import com.readalyse.entities.AgentTypeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentTypeRepository extends JpaRepository<AgentTypeEntity, Long> {
  Optional<AgentTypeEntity> findByName(String formatValue);
}
