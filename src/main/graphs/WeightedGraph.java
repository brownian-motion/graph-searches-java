package graphs;

import java.util.Set;

public interface WeightedGraph {
    Set<WeightedEdge> getNeighbors(int source);

    default boolean isConnected(int source, int neighbor) {
        return getNeighbors(source).stream().anyMatch(edge -> edge.neighbor() == neighbor);
    }

    default double getWeight(int source, int neighbor) {
        return getNeighbors(source).stream()
                .filter(edge -> edge.neighbor() == neighbor)
                .mapToDouble(WeightedEdge::cost)
                .findAny().orElse(Double.POSITIVE_INFINITY); // it is an infinite cost to travel on a path that's not allowed!
    }

}
