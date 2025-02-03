package com.readalyse.dataImport;

import com.readalyse.api.DataImportApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataImportController implements DataImportApi {
  private final DataImportService dataImportService;

  @Override
  public ResponseEntity<Void> triggerInformationRetrieval() {
    dataImportService.retrieveGutenbergInformation();
    return ResponseEntity.ok().build();
  }
}
