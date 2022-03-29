package graphs;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnweightedUndirectedAdjListGraphTest {

    @Test
    public void isConnected() {
        record EdgeCheck(int source, int dest, boolean isConnected) {}
        record TestCase(String name, UnweightedUndirectedAdjListGraph graph, EdgeCheck... checks) {}

        Arrays.asList(
                new TestCase(
                        "nodes are only connected to neighbors",
                        new UnweightedUndirectedAdjListGraph()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(1, 4),
                        new EdgeCheck(1, 2, true),
                        new EdgeCheck(1, 3, false),
                        new EdgeCheck(1, 4, true),
                        new EdgeCheck(2, 1, true),
                        new EdgeCheck(2, 3, true),
                        new EdgeCheck(2, 4, false),
                        new EdgeCheck(3, 1, false),
                        new EdgeCheck(3, 2, true),
                        new EdgeCheck(3, 4, false)
                ),
                new TestCase(
                        "nodes are not connected to unrelated nodes not in the graph",
                        new UnweightedUndirectedAdjListGraph()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(1, 4),
                        new EdgeCheck(1, 5, false),
                        new EdgeCheck(5, 1, false),
                        new EdgeCheck(2, 5, false),
                        new EdgeCheck(5, 2, false),
                        new EdgeCheck(3, 5, false),
                        new EdgeCheck(5, 3, false),
                        new EdgeCheck(4, 5, false),
                        new EdgeCheck(5, 5, false),
                        new EdgeCheck(0, 5, false),
                        new EdgeCheck(5, 0, false)
                ),
                new TestCase(
                        "empty graphs have no edges",
                        new UnweightedUndirectedAdjListGraph(),
                        new EdgeCheck(1, 2, false),
                        new EdgeCheck(1, 3, false),
                        new EdgeCheck(1, 4, false),
                        new EdgeCheck(2, 1, false),
                        new EdgeCheck(2, 3, false),
                        new EdgeCheck(2, 4, false),
                        new EdgeCheck(3, 1, false),
                        new EdgeCheck(3, 2, false),
                        new EdgeCheck(3, 4, false)
                )
        ).forEach(testCase -> {
            for (EdgeCheck check : testCase.checks) {
                assertEquals(check.isConnected, testCase.graph.isConnected(check.source, check.dest),
                        String.format("%s: edge %d -> %d %s be connected", testCase.name, check.source, check.dest, check.isConnected ? "should" : "should not"));
            }
        });
    }

    @org.junit.jupiter.api.Test
    public void getNeighbors() {
        record NeighborsCheck(int source, Integer... desiredNeighbors) {}
        record TestCase(String name, UnweightedUndirectedAdjListGraph graph, NeighborsCheck... checks) {}

        Arrays.asList(
                new TestCase(
                        "nodes are only connected to neighbors",
                        new UnweightedUndirectedAdjListGraph()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(1, 4),
                        new NeighborsCheck(1, 2, 4),
                        new NeighborsCheck(2, 3, 1),
                        new NeighborsCheck(3, 2),
                        new NeighborsCheck(4, 1)
                ),
                new TestCase(
                        "nodes are not connected to unrelated nodes not in the graph",
                        new UnweightedUndirectedAdjListGraph()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(1, 4),
                        new NeighborsCheck(5, new Integer[0]),
                        new NeighborsCheck(0, new Integer[0])
                ),
                new TestCase(
                        "empty graphs have no edges",
                        new UnweightedUndirectedAdjListGraph(),
                        new NeighborsCheck(1, new Integer[0]),
                        new NeighborsCheck(2, new Integer[0]),
                        new NeighborsCheck(3, new Integer[0]),
                        new NeighborsCheck(3, new Integer[0])
                )
        ).forEach(testCase -> {
            for (NeighborsCheck check : testCase.checks) {
                var expectedNeighbors = new HashSet<>(List.of(check.desiredNeighbors));
                var actualNeighbors = testCase.graph.getNeighbors(check.source);

                assertEquals(expectedNeighbors, actualNeighbors,
                        String.format("%s: node %d was not connected to the expected neighbors %s", testCase.name, check.source, Arrays.toString(check.desiredNeighbors)));
            }
        });
    }
}