package graphs;

import java.util.Set;

public interface UnweightedGraph {
    Set<Integer> getNeighbors(int source);

    default boolean isConnected(int source, int neighbor) {
        return getNeighbors(source).contains(neighbor);
    }
}
