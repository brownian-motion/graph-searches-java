import java.util.Set;

public abstract class AbstractGraph {
    protected abstract boolean isConnected(int source, int neighbor);

    protected abstract Set<Integer> getNeighbors(int node);
}
