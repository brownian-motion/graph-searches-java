package searches;

import graphs.SearchableGraph;
import graphs.WeightedDirectedAdjListGraph;

import java.util.List;
import java.util.Set;

public class DijkstrasSearch extends WeightedDirectedAdjListGraph implements SearchableGraph {

    // this method only exists so that I can write tests super easily
    @Override
    public DijkstrasSearch addEdge(int source, int neighbor, double weight) {
        super.addEdge(source, neighbor, weight);
        return this;
    }

    // this method only exists so that I can write tests super easily
    @Override
    public DijkstrasSearch addUndirectedEdge(int source, int neighbor, double weight) {
        super.addUndirectedEdge(source, neighbor, weight);
        return this;
    }

    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return djikstrasSearch(source, dest);
    }

    public List<Integer> djikstrasSearch(int source, int dest) {

        // TODO: put your code here!
        return null;
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {
        // TODO: put your code here!
        return false;
    }
}
