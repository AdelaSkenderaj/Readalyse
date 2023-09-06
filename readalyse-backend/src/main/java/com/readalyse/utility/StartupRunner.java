package com.readalyse.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

  public final InformationExtraction informationExtraction;

  private static String basePath = "C:/Users/Dela/test";

  @Override
  public void run(String... args) throws Exception {
    //        informationExtraction.extractInformation(new File(basePath));
  }
}
