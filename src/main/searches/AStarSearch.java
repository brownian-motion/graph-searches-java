package searches;

import graphs.SearchableGraph;
import graphs.WeightedDirectedAdjListGraph;

import java.util.List;
import java.util.Set;

public class AStarSearch extends WeightedDirectedAdjListGraph implements SearchableGraph {

    private final AStarDistanceEstimator estimator;

    public AStarSearch(AStarDistanceEstimator estimator) {
        this.estimator = estimator;
    }

    // this method only exists so that I can write tests super easily
    @Override
    public AStarSearch addEdge(int source, int neighbor, double weight) {
        super.addEdge(source, neighbor, weight);
        return this;
    }

    // this method only exists so that I can write tests super easily
    @Override
    public AStarSearch addUndirectedEdge(int source, int neighbor, double weight) {
        super.addUndirectedEdge(source, neighbor, weight);
        return this;
    }

    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return aStarSearch(source, dest);
    }

    public List<Integer> aStarSearch(int source, int dest) {
        // TODO: put your code here!
        return null;
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {

        // TODO: put your code here!
        return false;
    }

}
