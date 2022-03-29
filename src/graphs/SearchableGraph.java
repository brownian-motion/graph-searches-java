package graphs;

import java.util.List;
import java.util.Set;

public interface SearchableGraph {
    List<Integer> findShortestPath(int source, int dest);

    boolean canReachEveryNode(int source, Set<Integer> allNodes);
}
