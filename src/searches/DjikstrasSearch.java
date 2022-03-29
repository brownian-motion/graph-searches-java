package searches;

import graphs.WeightedDirectedAdjListGraph;

import java.util.List;
import java.util.Set;

public class DjikstrasSearch extends WeightedDirectedAdjListGraph {

    // this method only exists so that I can write tests super easily
    @Override
    public DjikstrasSearch addEdge(int source, int neighbor, double weight) {
        super.addEdge(source, neighbor, weight);
        return this;
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
