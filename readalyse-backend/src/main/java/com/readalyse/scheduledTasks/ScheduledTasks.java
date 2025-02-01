package com.readalyse.scheduledTasks;

import com.readalyse.utility.FileRetrieval;
import com.readalyse.utility.InformationExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private final InformationExtraction informationExtraction;
  private final FileRetrieval fileRetrieval;

  /*@Scheduled(cron = "0 57 13 * * *")
  public void getLatestBooks() {
    System.out.println("daily task started");
    try {
      fileRetrieval.initFileRetrieval(
          "C:/Users/Dela/background-workers/daily-data",
          "C:/Users/Dela/background-workers/daily-data/rdf-files.tar.bz2",
          "https://gutenberg.org/cache/epub/feeds/rdf-files.tar.bz2");
      informationExtraction.extractInformation(
          new File("C:/Users/Dela/background-workers/all-time-data"));
      fileRetrieval.deleteFiles();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }*/
}
