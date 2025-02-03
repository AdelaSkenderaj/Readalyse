package com.readalyse.dataImport;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataImportService {

  private final ZipProcessingService zipProcessingService;

  @Async
  public void retrieveGutenbergInformation() {
    zipProcessingService.fetchAndProcessZip();
  }
}
