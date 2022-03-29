package searches;

import graphs.SearchableGraph;

import java.util.Set;

record CanReachEveryNodeTestCase(String name, SearchableGraph graph, Set<Integer> allNodes,
                                 boolean canReachAllFromOne) {}
