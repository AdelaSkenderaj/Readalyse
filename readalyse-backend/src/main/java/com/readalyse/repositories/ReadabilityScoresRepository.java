package com.readalyse.repositories;

import com.readalyse.entities.ReadabilityScoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadabilityScoresRepository extends JpaRepository<ReadabilityScoresEntity, Long> {
}
