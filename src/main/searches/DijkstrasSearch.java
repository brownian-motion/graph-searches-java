package searches;

import graphs.GraphSearch;
import graphs.ReachabilitySearch;
import graphs.WeightedGraph;

import java.util.List;
import java.util.Set;

/**
 * Dijkstra's algorithm is a breadth-first search generalized for a weighted graph with non-negative weights.
 * Use your experience from implementing BFS to implement the two marked methods below,
 * {@link #dijkstrasSearch(int, int)} and {@link #canReachEveryNode(int, Set)},
 * so that all the tests in {@code BreadthFirstSearchTest} pass.
 */
public record DijkstrasSearch(WeightedGraph graph) implements GraphSearch, ReachabilitySearch {
    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return dijkstrasSearch(source, dest);
    }

    public List<Integer> dijkstrasSearch(int source, int dest) {

        // TODO: put your code here!
        return null;
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {
        // TODO: put your code here!
        return false;
    }
}
