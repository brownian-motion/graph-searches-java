package searches;

import graphs.SearchableGraph;

record GraphSearchTestCase(String name, SearchableGraph graph, SearchResult... searches) {}
