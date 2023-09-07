package com.readalyse.util;

public class Constants {

  private Constants() {}

  public static final String TERMS =
      "PREFIX base: <http://www.gutenberg.org/>"
          + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
          + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
          + "PREFIX dcterms: <http://purl.org/dc/terms/>"
          + "PREFIX pgterms: <http://www.gutenberg.org/2009/pgterms/>"
          + "PREFIX cc: <http://web.resource.org/cc/>"
          + "PREFIX dcam: <http://purl.org/dc/dcam/>"
          + "PREFIX marcrel: <http://id.loc.gov/vocabulary/relators/>";

  public static final String PROJECT_GUTENBERG_MARKERS_PATTERN =
      "(?s)\\*{3}\\s*START OF THE PROJECT GUTENBERG EBOOK[^*]+\\*{3}(.*?)\\*{3}\\s*END OF THE PROJECT GUTENBERG EBOOK[^*]+\\*{3}";
}
