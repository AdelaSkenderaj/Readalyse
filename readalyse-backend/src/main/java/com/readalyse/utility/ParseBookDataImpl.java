package com.readalyse.utility;

import static com.readalyse.utility.Constants.*;

import com.readalyse.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Component;

@Component
public class ParseBookDataImpl implements ParseBookData {
  @Override
  public BookModel getBaseBookInformation(Model model) {
    BookModel book = null;
    Query query = QueryFactory.create(BOOK_INFORMATION_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal title = soln.getLiteral("title");
        Literal description = soln.getLiteral("description");
        Literal downloads = soln.getLiteral("downloads");
        org.apache.jena.rdf.model.Resource ebook = soln.getResource("ebook");

        book =
            BookModel.builder()
                .id(Long.valueOf(formatValue(ebook)))
                .title(formatValue(title))
                .description(formatValue(description))
                .downloads(Long.valueOf(formatValue(downloads)))
                .build();
      }
    }
    return book;
  }

  @Override
  public List<BookshelfModel> getBookshelves(Model model) {
    List<BookshelfModel> bookshelves = new ArrayList<>();
    Query query = QueryFactory.create(BOOKSHELVES_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal name = soln.getLiteral("value");

        BookshelfModel bookshelf = BookshelfModel.builder().name(formatValue(name)).build();
        bookshelves.add(bookshelf);
      }
    }
    return bookshelves;
  }

  @Override
  public List<LanguageModel> getLanguages(Model model) {
    List<LanguageModel> languages = new ArrayList<>();
    Query query = QueryFactory.create(LANGUAGES_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        LanguageModel language = LanguageModel.builder().language(formatValue(value)).build();
        languages.add(language);
      }
    }
    return languages;
  }

  @Override
  public List<SubjectModel> getSubjects(Model model) {
    List<SubjectModel> subjects = new ArrayList<>();
    Query query = QueryFactory.create(SUBJECTS_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        SubjectModel subject = SubjectModel.builder().name(formatValue(value)).build();
        subjects.add(subject);
      }
    }
    return subjects;
  }

  @Override
  public List<ResourceModel> getResources(Model model) {
    List<ResourceModel> resources = new ArrayList<>();
    Query query = QueryFactory.create(RESOURCES_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal extent = soln.getLiteral("extent");
        Literal modified = soln.getLiteral("modified");
        Literal value = soln.getLiteral("value");
        org.apache.jena.rdf.model.Resource file = soln.getResource("file");

        String type = Optional.ofNullable(value).map(Literal::toString).orElse(null);

        ResourceModel resource =
            ResourceModel.builder()
                .url(
                    Optional.ofNullable(file)
                        .map(org.apache.jena.rdf.model.Resource::toString)
                        .orElse(null))
                .size(Long.valueOf(formatValue(extent)))
                .modified(LocalDateTime.parse(formatValue(modified)))
                .type(
                    type != null && type.contains("^")
                        ? type.substring(0, type.indexOf('^'))
                        : type)
                .build();
        resources.add(resource);
      }
    }
    return resources;
  }

  @Override
  public List<AgentModel> getAgents(Model model) {
    List<AgentModel> agents = new ArrayList<>();
    Query query = QueryFactory.create(AGENT_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal name = soln.getLiteral("name");
        Literal birthdate = soln.getLiteral("birthdate");
        Literal deathdate = soln.getLiteral("deathdate");
        Literal alias = soln.getLiteral("aliases");
        org.apache.jena.rdf.model.Resource webpage = soln.getResource("webpage");
        org.apache.jena.rdf.model.Resource id = soln.getResource("agent");
        Literal predicate = soln.getLiteral("predicate");

        Long birthdateValue = birthdate != null ? Long.parseLong(formatValue(birthdate)) : null;
        Long deathdateValue = deathdate != null ? Long.parseLong(formatValue(deathdate)) : null;

        AgentTypeModel agentType = AgentTypeModel.builder().name(formatValue(predicate)).build();
        PersonModel person =
            PersonModel.builder()
                .id(Long.valueOf(formatValue(id)))
                .name(formatValue(name))
                .alias(formatValue(alias))
                .birthdate(birthdateValue)
                .deathdate(deathdateValue)
                .webpage(
                    Optional.ofNullable(webpage)
                        .map(org.apache.jena.rdf.model.Resource::toString)
                        .orElse(null))
                .build();
        AgentModel agent = AgentModel.builder().person(person).type(agentType).build();
        agents.add(agent);
      }
    }
    return agents;
  }

  private String formatValue(Literal value) {
    String formattedValue = Optional.ofNullable(value).map(Literal::getString).orElse(null);
    if (formattedValue != null && formattedValue.contains("^^")) {
      formattedValue = formattedValue.substring(0, formattedValue.indexOf('^'));
    }

    if (formattedValue != null && formattedValue.contains("/")) {
      formattedValue = formattedValue.substring(formattedValue.lastIndexOf('/') + 1);
    }
    return formattedValue;
  }

  private static String formatValue(org.apache.jena.rdf.model.Resource value) {
    String formattedValue =
        Optional.ofNullable(value).map(org.apache.jena.rdf.model.Resource::toString).orElse(null);
    if (formattedValue != null && formattedValue.contains("^^")) {
      formattedValue = formattedValue.substring(0, formattedValue.indexOf('^'));
    }

    if (formattedValue != null && formattedValue.contains("/")) {
      formattedValue = formattedValue.substring(formattedValue.lastIndexOf('/') + 1);
    }
    return formattedValue;
  }
}
