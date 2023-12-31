package com.readalyse.utility;

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

  public static final String BOOK_INFORMATION_QUERY =
      TERMS
          + "SELECT ?ebook ?title ?description ?downloads\n"
          + "WHERE {\n"
          + "  ?ebook rdf:type pgterms:ebook.\n"
          + "  OPTIONAL { ?ebook dcterms:title ?title. }\n"
          + "  OPTIONAL { ?ebook dcterms:description ?description. }\n"
          + "  OPTIONAL { ?ebook pgterms:downloads ?downloads. }\n"
          + "}";

  public static final String BOOKSHELVES_QUERY =
      TERMS
          + "SELECT ?value\n"
          + "WHERE {\n"
          + "    ?ebook pgterms:bookshelf ?Description .\n"
          + "    OPTIONAL { ?Description rdf:value ?value. }\n"
          + "}";

  public static final String LANGUAGES_QUERY =
      TERMS
          + "SELECT ?value\n"
          + "WHERE {\n"
          + "    ?ebook dcterms:language ?Description .\n"
          + "    OPTIONAL { ?Description rdf:value ?value. }\n"
          + "}";

  public static final String SUBJECTS_QUERY =
      TERMS
          + "SELECT ?value\n"
          + "WHERE {\n"
          + "    ?ebook dcterms:subject ?Description .\n"
          + "    OPTIONAL { ?Description rdf:value ?value. }\n"
          + "}";

  public static final String RESOURCES_QUERY =
      TERMS
          + "SELECT ?file ?extent ?modified ?value\n"
          + "WHERE {\n"
          + "  {\n"
          + "    SELECT ?ebook ?file\n"
          + "    WHERE {\n"
          + "      ?ebook dcterms:hasFormat ?file .\n"
          + "    }\n"
          + "  }\n"
          + "  OPTIONAL { ?file dcterms:extent ?extent. }\n"
          + "  OPTIONAL { ?file dcterms:modified ?modified. }\n"
          + "  OPTIONAL {\n"
          + "    ?file dcterms:format ?Description .\n"
          + "    ?Description rdf:value ?value .\n"
          + "  }\n"
          + "}";

  public static final String AGENT_QUERY =
      TERMS
          + "SELECT ?agent ?predicate ?name ?birthdate ?deathdate (GROUP_CONCAT(?alias; SEPARATOR=\"; \") AS ?aliases) ?webpage\n"
          + "WHERE {\n"
          + " { "
          + "   ?ebook ?typeProperty ?agent . "
          + "   VALUES (?typeProperty ?type) {\n"
          + "    (dcterms:creator          \"Creator\")\n"
          + "    (marcrel:ill              \"Illustrator\")\n"
          + "    (marcrel:pht              \"Photographer\")\n"
          + "    (marcrel:ann              \"Annotator\")\n"
          + "    (marcrel:cmm              \"Contributor\")\n"
          + "    (marcrel:trl              \"Translator\")\n"
          + "    (marcrel:com              \"Compiler\")\n"
          + "    (marcrel:cmp              \"Composer\")\n"
          + "    (marcrel:prf              \"Performer\")\n"
          + "    (marcrel:edt              \"Editor\")\n"
          + "    (marcrel:ctb              \"Contributor\")\n"
          + "    (marcrel:aui              \"Author Of Introduction\")\n"
          + "  } "
          + "     BIND(COALESCE(?type, \"\") AS ?predicate)\n "
          + " } "
          + "  OPTIONAL { ?agent pgterms:name ?name. }\n"
          + "  OPTIONAL { ?agent pgterms:birthdate ?birthdate. }\n"
          + "  OPTIONAL { ?agent pgterms:deathdate ?deathdate. }\n"
          + "  OPTIONAL { ?agent pgterms:alias ?alias. }\n"
          + "  OPTIONAL {\n"
          + "    SELECT ?agent ?webpage "
          + "    WHERE { "
          + "       ?agent pgterms:webpage ?webpage .\n"
          + "    } "
          + "    LIMIT 1 "
          + "  }\n"
          + "}\n"
          + "GROUP BY ?agent ?predicate ?name ?birthdate ?deathdate ?webpage";

  public static final String PROJECT_GUTENBERG_MARKERS_PATTERN =
      "(?s)\\*{3}\\s*START OF THE PROJECT GUTENBERG EBOOK[^*]+\\*{3}(.*?)\\*{3}\\s*END OF THE PROJECT GUTENBERG EBOOK[^*]+\\*{3}";
}
