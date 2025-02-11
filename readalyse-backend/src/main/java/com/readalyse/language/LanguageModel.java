package com.readalyse.language;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageModel {
  private Long id;
  private String language;
}
