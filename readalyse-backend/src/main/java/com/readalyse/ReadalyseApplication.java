package com.readalyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReadalyseApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReadalyseApplication.class, args);
  }
}
