package searches;

import graphs.UnweightedUndirectedAdjListGraph;

import java.util.List;
import java.util.Set;

public class BreadthFirstSearch extends UnweightedUndirectedAdjListGraph {

    // this method only exists so that I can write tests super easily
    @Override
    public BreadthFirstSearch addEdge(int source, int neighbor) {
        super.addEdge(source, neighbor);
        return this;
    }

    public List<Integer> breadthFirstSearch(int source, int dest) {

        // TODO: put your code here!
        return null;
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {
        // TODO: put your code here!
        return false;
    }
}
