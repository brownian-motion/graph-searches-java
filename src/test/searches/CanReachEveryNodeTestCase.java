package searches;

import graphs.ReachabilitySearch;

import java.util.Set;

public record CanReachEveryNodeTestCase(String name, ReachabilitySearch graph, Set<Integer> allNodes,
                                        boolean canReachAllFromOne) {}
