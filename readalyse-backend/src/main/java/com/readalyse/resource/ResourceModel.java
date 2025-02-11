package com.readalyse.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceModel {
  private Long id;
  private String url;
  private Long size;
  private String modified;
  private String type;
}
