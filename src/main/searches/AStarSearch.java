package searches;

import graphs.GraphSearch;
import graphs.WeightedGraph;

import java.util.List;

public record AStarSearch(AStarDistanceEstimator estimator, WeightedGraph graph) implements GraphSearch {

    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return aStarSearch(source, dest);
    }

    public List<Integer> aStarSearch(int source, int dest) {
        // TODO: put your code here!
        return null;
    }

}
