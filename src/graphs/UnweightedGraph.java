package graphs;

import java.util.Set;

public interface UnweightedGraph<G extends UnweightedGraph<G>> {

    default boolean isConnected(int source, int neighbor) {
        return getNeighbors(source).contains(neighbor);
    }

    public abstract Set<Integer> getNeighbors(int node);

    // this is only here so I can build graphs easily in tests!
    G addEdge(int source, int dest);

}
