package com.readalyse.utility;

import com.readalyse.model.*;
import java.util.List;
import org.apache.jena.rdf.model.Model;

public interface ParseBookData {

  Book getBaseBookInformation(Model model);

  List<Bookshelf> getBookshelves(Model model);

  List<Language> getLanguages(Model model);

  List<Subject> getSubjects(Model model);

  List<Resource> getResources(Model model);

  List<Agent> getAgents(Model model);
}
