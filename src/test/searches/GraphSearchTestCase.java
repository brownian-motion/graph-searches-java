package searches;

import graphs.GraphSearch;

public record GraphSearchTestCase(String name, GraphSearch graph, SearchResult... searches) {}
