package com.readalyse.utility;

import eu.crydee.syllablecounter.SyllableCounter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import lombok.Data;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

@Data
public class BookData {

  private Long letters = 0L;
  private Long words = 0L;
  private Long sentences = 0L;
  private Long nrSyllables = 0L;
  private Long complexWords = 0L;
  private Long polysyllabicWords = 0L;
  private Long singleSyllableWords = 0L;
  private Long longWords = 0L;
  private Long unfamiliarWords = 0L;
  private Long difficultWords = 0L;

  public BookData(String text) {
    analyzeText(text);
  }

  private void analyzeText(String text) {
    try (InputStream modelIn =
        new FileInputStream(
            "readalyse-backend/src/main/resources/sentence_detection/en-sent.bin")) { // Provide the
      // path to the
      SentenceModel model = new SentenceModel(modelIn);
      SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

      // Tokenize text into sentences
      String[] sentences = sentenceDetector.sentDetect(text);
      this.sentences = (long) sentences.length;

      SyllableCounter counter = new SyllableCounter();
      Arrays.stream(sentences)
          .forEach(
              sentence -> {
                String[] words = sentence.split("\\s+");
                this.words = this.words + words.length;
                Arrays.stream(words)
                    .forEach(
                        word -> {
                          if (word.length() > 6) this.longWords++;
                          int syllables = counter.count(word);
                          this.nrSyllables = this.nrSyllables + syllables;
                          if (syllables == 1) this.singleSyllableWords++;
                          else this.polysyllabicWords++;
                        });
                this.letters = this.letters + sentence.replaceAll("\\s+", "").length();
              });
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
