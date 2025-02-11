package com.readalyse.language;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
  Optional<LanguageEntity> findByLanguage(String formatValue);

  List<LanguageEntity> findByLanguageIn(Set<String> languages);
}
