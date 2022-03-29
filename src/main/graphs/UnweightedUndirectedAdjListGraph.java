package graphs;

import java.util.*;

public class UnweightedUndirectedAdjListGraph implements UnweightedGraph<UnweightedUndirectedAdjListGraph> {
    private final Map<Integer, Set<Integer>> neighbors = new HashMap<>();

    public UnweightedUndirectedAdjListGraph addEdge(int source, int neighbor) {
        neighbors.computeIfAbsent(source, HashSet::new).add(neighbor);
        neighbors.computeIfAbsent(neighbor, HashSet::new).add(source);
        return this;
    }

    @Override
    public Set<Integer> getNeighbors(int node) {
        return Collections.unmodifiableSet(neighbors.getOrDefault(node, Collections.emptySet()));
    }

    @Override
    public String toString() {
        return String.format("{undirected unweighted graph with nodes %s and edges %s}",
                // sorted printouts are easier for humans to use when debugging
                new TreeSet<>(neighbors.keySet()),
                new TreeMap<>(neighbors));
    }
}
