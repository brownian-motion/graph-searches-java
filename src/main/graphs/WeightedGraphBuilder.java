package graphs;

public interface WeightedGraphBuilder<G extends WeightedGraphBuilder<G>> {

    G addEdge(int source, int dest, double weight);
}