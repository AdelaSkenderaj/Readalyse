package com.readalyse.dataImport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GutenbergClient {

  @Value("${projectGutenberg.url}")
  private String url;

  @Bean
  public WebClient webClient() {
    return WebClient.builder().baseUrl(url).build();
  }
}
