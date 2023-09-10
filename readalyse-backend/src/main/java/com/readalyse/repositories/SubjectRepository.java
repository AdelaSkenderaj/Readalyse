package com.readalyse.repositories;

import com.readalyse.entities.SubjectEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
  Optional<SubjectEntity> findByName(String formatValue);
}
