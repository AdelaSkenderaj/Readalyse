package com.readalyse.repositories;

import com.readalyse.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
  SubjectEntity findByName(String formatValue);
}
