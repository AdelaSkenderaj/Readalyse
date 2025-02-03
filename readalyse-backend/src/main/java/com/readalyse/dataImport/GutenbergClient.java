package com.readalyse.dataImport;

import org.springframework.beans.factory.annotation.Value;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class GutenbergClient {

    @Value("${projectGutenberg.url}")
    private String url;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(url).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }
}
