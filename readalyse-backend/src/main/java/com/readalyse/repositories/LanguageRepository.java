package com.readalyse.repositories;

import com.readalyse.entities.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
  LanguageEntity findByLanguage(String formatValue);
}