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
  public Book getBaseBookInformation(Model model) {
    Book book = null;
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
            Book.builder()
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
  public List<Bookshelf> getBookshelves(Model model) {
    List<Bookshelf> bookshelves = new ArrayList<>();
    Query query = QueryFactory.create(BOOKSHELVES_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal name = soln.getLiteral("value");

        Bookshelf bookshelf = Bookshelf.builder().name(formatValue(name)).build();
        bookshelves.add(bookshelf);
      }
    }
    return bookshelves;
  }

  @Override
  public List<Language> getLanguages(Model model) {
    List<Language> languages = new ArrayList<>();
    Query query = QueryFactory.create(LANGUAGES_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        Language language = Language.builder().language(formatValue(value)).build();
        languages.add(language);
      }
    }
    return languages;
  }

  @Override
  public List<Subject> getSubjects(Model model) {
    List<Subject> subjects = new ArrayList<>();
    Query query = QueryFactory.create(SUBJECTS_QUERY);
    try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
      ResultSet result = qexec.execSelect();
      while (result.hasNext()) {
        QuerySolution soln = result.nextSolution();
        Literal value = soln.getLiteral("value");

        Subject subject = Subject.builder().name(formatValue(value)).build();
        subjects.add(subject);
      }
    }
    return subjects;
  }

  @Override
  public List<Resource> getResources(Model model) {
    List<Resource> resources = new ArrayList<>();
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

        Resource resource =
            Resource.builder()
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
  public List<Agent> getAgents(Model model) {
    List<Agent> agents = new ArrayList<>();
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

        AgentType agentType = AgentType.builder().name(formatValue(predicate)).build();
        Person person =
            Person.builder()
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
        Agent agent = Agent.builder().person(person).type(agentType).build();
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
