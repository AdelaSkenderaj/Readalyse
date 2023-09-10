package com.readalyse.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {
  private Long id;
  private String url;
  private Long size;
  private LocalDateTime modified;
  private String type;
}
