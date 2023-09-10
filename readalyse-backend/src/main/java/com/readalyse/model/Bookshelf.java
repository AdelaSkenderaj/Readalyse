package com.readalyse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bookshelf {
  private Long id;
  private String name;
}
