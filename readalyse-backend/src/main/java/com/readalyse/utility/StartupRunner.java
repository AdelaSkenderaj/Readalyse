package com.readalyse.utility;

import com.readalyse.entities.JobExecutionHistory;
import com.readalyse.repositories.JobExecutionHistoryRepository;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

  private final InformationExtraction informationExtraction;
  private final JobExecutionHistoryRepository jobExecutionHistoryRepository;
  private final FileRetrieval fileRetrieval;

  private static String basePath = "C:/Users/Dela/test";

  @Override
  public void run(String... args) throws Exception {
    System.out.println("startup runner started");
    String jobName = "OneTimeDatabaseInsertJob";
    JobExecutionHistory jobExecutionHistory =
        jobExecutionHistoryRepository.findById(jobName).orElse(null);
    if (jobExecutionHistory == null) {
      fileRetrieval.initFileRetrieval(
          "C:/Users/Dela/background-workers/all-time-data",
          "C:/Users/Dela/background-workers/all-time-data/rdf-files.tar.bz2",
          "https://gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2");
      informationExtraction.extractInformation(
          new File("C:/Users/Dela/background-workers/all-time-data"));
      JobExecutionHistory job = new JobExecutionHistory(jobName);
      jobExecutionHistoryRepository.save(job);
    }
    System.out.println("startup runner finished");
  }
}
