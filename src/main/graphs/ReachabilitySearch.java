package graphs;

import java.util.Set;

public interface ReachabilitySearch {
    boolean canReachEveryNode(int source, Set<Integer> allNodes);
}
