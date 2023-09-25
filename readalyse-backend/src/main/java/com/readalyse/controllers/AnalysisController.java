package com.readalyse.controllers;

import com.readalyse.api.AnalyseApi;
import com.readalyse.mappers.ReadabilityScoresMapper;
import com.readalyse.model.ReadabilityScores;
import com.readalyse.model.Text;
import com.readalyse.utility.AnalyzeText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalysisController implements AnalyseApi {

  private final AnalyzeText analyzeText;
  private final ReadabilityScoresMapper readabilityScoresMapper;

  @Override
  public ResponseEntity<ReadabilityScores> analyseText(Text text) {
    return ResponseEntity.ok(
        readabilityScoresMapper.entityToModel(analyzeText.calculateScores(text.getText())));
  }
}
