package graphs;

import java.util.Set;

/**
 * An algorithm that can determine whether every node in a graph is reachable from a single source.
 */
public interface ReachabilitySearch {
    /**
     * Searches the graph and returns whether there is a path to every node in {@code allNodes} starting from {@code source}.
     *
     * @param source   the identifier for the node to start searching from; may be any integer, and might have no edges
     * @param allNodes the set of all the vertices in this graph
     */
    boolean canReachEveryNode(int source, Set<Integer> allNodes);
}
