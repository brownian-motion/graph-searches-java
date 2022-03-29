package graphs;

import java.util.*;

public class WeightedDirectedAdjListGraph implements WeightedGraph<WeightedDirectedAdjListGraph> {
    private final Map<Integer, Set<WeightedEdge>> adjList = new HashMap<>();

    public WeightedDirectedAdjListGraph addEdge(int source, int neighbor, double weight) {
        adjList.computeIfAbsent(source, HashSet::new).add(new WeightedEdge(source, neighbor, weight));
        return this;
    }

    @Override
    public Set<WeightedEdge> getNeighbors(int node) {
        return Collections.unmodifiableSet(adjList.getOrDefault(node, Collections.emptySet()));
    }

    @Override
    public String toString() {
        return String.format("{directed weighted graph with nodes %s and edges %s}",
                // sorted printouts are easier for humans to use when debugging
                new TreeSet<>(adjList.keySet()),
                new TreeMap<>(adjList));
    }
}
