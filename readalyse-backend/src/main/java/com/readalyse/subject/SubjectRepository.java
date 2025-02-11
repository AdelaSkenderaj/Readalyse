package com.readalyse.subject;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
  Optional<SubjectEntity> findByName(String formatValue);

  List<SubjectEntity> findByNameIn(Set<String> subjects);
}
