package graphs;

import java.util.List;

/**
 * An algorithm that can find the shortest path between two nodes in a graph.
 */
public interface GraphSearch {
    /**
     * Searches the graph and returns the shortest path from source to destination, if possible.
     * May return {@code null} or an empty List if no such path exists.
     * Should return a singleton List if the source and destination are the same.
     *
     * @param source the identifier for the node to start searching from; may be any integer, and might have no edges
     * @param dest   the identifier for the node to search for from the source; may be any integer, and might have no edges
     */
    List<Integer> findShortestPath(int source, int dest);
}
