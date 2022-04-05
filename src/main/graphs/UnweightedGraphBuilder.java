package graphs;

public interface UnweightedGraphBuilder<G extends UnweightedGraphBuilder<G>> {

    // this is only here so I can build graphs easily in tests!
    G addEdge(int source, int dest);

}
