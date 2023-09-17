package com.readalyse.utility;

import static com.readalyse.utility.Constants.PROJECT_GUTENBERG_MARKERS_PATTERN;

import com.readalyse.entities.ReadabilityScoresEntity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class AnalyzeText {
  Pattern regex = Pattern.compile(PROJECT_GUTENBERG_MARKERS_PATTERN, Pattern.MULTILINE);

  public String formatText(String text) {
    Matcher matcher = regex.matcher(text);
    String extractedText = "";
    if (matcher.find()) {
      extractedText = matcher.group(1).trim();
    }
    return !"".equals(extractedText) ? extractedText : text;
  }

  public ReadabilityScoresEntity calculateScores(String text) {
    text = formatText(text);
    BookData data = new BookData(text);
    return ReadabilityScoresEntity.builder()
        .fleschKincaidGradeLevel(calculateFleschKincaidGradeLevel(data))
        .fleschReadingEase(calculateFleschReadingEase(data))
        .colemanLiauIndex(colemanLiauIndex(data))
        .smogIndex(smogIndex(data))
        .automatedReadabilityIndex(calculateAutomatedReabilityIndex(data))
        .forcastIndex(forcast(data))
        //                    .powersSumnerKearl(powersSumnerKearl(data))
        .lixIndex(lixFormula(data))
        .rixIndex(rixFormula(data))
        .build();
  }

  private Double calculateFleschKincaidGradeLevel(BookData data) {
    return (0.39 * ((double) data.getWords() / data.getSentences())
        + 11.8 * ((double) data.getNrSyllables() / data.getWords())
        - 15.59);
  }

  private Double calculateFleschReadingEase(BookData data) {
    return (206.835
        - 1.015 * ((double) data.getWords() / data.getSentences())
        - 84.6 * ((double) data.getNrSyllables() / data.getWords()));
  }

  private Double colemanLiauIndex(BookData data) {
    return (0.0588 * ((data.getLetters() / (double) data.getWords()) * 100)
        - 0.296 * ((data.getSentences() / (double) data.getWords()) * 100)
        - 15.8);
  }

  private Double smogIndex(BookData data) {
    return (1.0430 * Math.sqrt((double) data.getPolysyllabicWords() * 30 / data.getSentences())
        + 3.1291);
  }

  private Double calculateAutomatedReabilityIndex(BookData data) {
    return (4.71 * ((double) data.getLetters() / data.getWords())
        + 0.5 * ((double) data.getWords() / data.getSentences())
        - 21.43);
  }

  private Double forcast(BookData data) {
    return (20 - ((double) data.getSingleSyllableWords() / 1500));
  }

  private Double powersSumnerKearl(BookData data) {
    return (0.0778 * ((double) data.getWords() / data.getSentences())
        + 0.0455 * data.getNrSyllables()
        + 2.7971);
  }

  private Double lixFormula(BookData data) {
    return (((double) data.getLongWords() * 100 / data.getWords())
        + ((double) data.getWords() / data.getSentences()));
  }

  private Double rixFormula(BookData data) {
    return ((double) data.getLongWords() / data.getSentences());
  }
}
