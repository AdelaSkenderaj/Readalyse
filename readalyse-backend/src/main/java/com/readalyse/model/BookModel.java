package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookModel {
  private Long id;
  private String title;
  private String description;
  private Long downloads;
  private String type;
}
