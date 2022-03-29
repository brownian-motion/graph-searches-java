package searches;

import graphs.SearchableGraph;
import graphs.UnweightedUndirectedAdjListGraph;

import java.util.List;
import java.util.Set;

/**
 * Breadth-first searches are very efficient, but only work for unweighted graphs.
 * Implement the two marked methods below,
 * {@link #breadthFirstSearch(int, int)} and {@link #canReachEveryNode(int, Set)},
 * so that all the tests in {@code BreadthFirstSearchTest} pass.
 */
public class BreadthFirstSearch extends UnweightedUndirectedAdjListGraph implements SearchableGraph {

    // this method only exists so that I can write tests super easily
    @Override
    public BreadthFirstSearch addEdge(int source, int neighbor) {
        super.addEdge(source, neighbor);
        return this;
    }

    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return breadthFirstSearch(source, dest);
    }

    public List<Integer> breadthFirstSearch(int source, int dest) {

        // TODO: put your code here!
        return null;
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {
        // TODO: put your code here!
        return false;
    }
}
