package searches.answers;

import graphs.SearchableGraph;

public record GraphSearchTestCase(String name, SearchableGraph graph, SearchResult... searches) {}
