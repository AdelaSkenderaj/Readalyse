package com.readalyse.dataImport;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class DataImportService {

  @Value("${projectGutenberg.url}")
  private String url;

  @Value("${projectGutenberg.downloadBaseDirectory}")
  private String downloadBaseDirectory;

  @Value("${projectGutenberg.downloadedFilesPath}")
  private String downloadedFilesPath;

  private final FileRetrieval fileRetrieval;
  private final InformationExtraction informationExtraction;

  public void retrieveGutebergInformation() {
    try {
      this.fileRetrieval.initFileRetrieval(downloadBaseDirectory, downloadedFilesPath, url);
      this.informationExtraction.extractInformation(new File(downloadBaseDirectory));
      this.fileRetrieval.deleteFiles();
    } catch (Exception e) {
      System.out.println("An error occurred while retrieving information from Project Gutenberg");
    }
  }
}
