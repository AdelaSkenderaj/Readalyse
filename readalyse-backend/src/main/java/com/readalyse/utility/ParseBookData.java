package com.readalyse.utility;

import com.readalyse.model.*;
import java.util.List;
import org.apache.jena.rdf.model.Model;

public interface ParseBookData {

  BookModel getBaseBookInformation(Model model);

  List<BookshelfModel> getBookshelves(Model model);

  List<LanguageModel> getLanguages(Model model);

  List<SubjectModel> getSubjects(Model model);

  List<ResourceModel> getResources(Model model);

  List<AgentModel> getAgents(Model model);
}
