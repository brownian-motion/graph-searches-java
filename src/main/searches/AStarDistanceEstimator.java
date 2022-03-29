package searches;

public interface AStarDistanceEstimator {
    // This is the heuristic for an A* search.
    // It estimates the distance from the specified source node to the goal node.
    // It is allowed to underestimate, but must not over-estimate!
    double estimateCostToEnd(int source, int goal);
}
