package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageModel {
  private Long id;
  private String language;
}
