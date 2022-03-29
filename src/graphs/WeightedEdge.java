package graphs;

public record WeightedEdge(int source, int neighbor, double cost) {
    @Override
    public String toString() {
        return "{%d->%d, cost=%s}".formatted(source, neighbor, cost);
    }
}
