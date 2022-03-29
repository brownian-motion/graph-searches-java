package searches.answers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import searches.DijkstrasSearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DijkstrasSearchTest {

    @ParameterizedTest
    @MethodSource("getDijkstrasSearchTestCases")
    public void dijkstrasSearch(GraphSearchTestCase testCase) {
        for (var search : testCase.searches()) {
            List<Integer> actualPath = testCase.graph().findShortestPath(search.start(), search.end());

            if (search.bestPath() == null || search.bestPath().isEmpty()) {
                Assertions.assertTrue(actualPath == null || actualPath.isEmpty(), String.format("%s: search for %d -> %d through %s should have failed, but yielded %s",
                        testCase.name(), search.start(), search.end(), testCase.graph(), actualPath));
            } else {
                Assertions.assertEquals(search.bestPath(), actualPath,
                        String.format("%s: search for %d -> %d through %s yielded wrong path",
                                testCase.name(), search.start(), search.end(), testCase.graph()));
            }
        }
    }

    static GraphSearchTestCase[] getDijkstrasSearchTestCases() {
        return new GraphSearchTestCase[]{
                new GraphSearchTestCase("empty graph always returns an empty list or null", new DijkstrasSearch(),
                        new SearchResult(1, 2, Collections.emptyList()),
                        new SearchResult(2, 1, Collections.emptyList()),
                        new SearchResult(5, 1, Collections.emptyList())
                ),
                new GraphSearchTestCase("straight line",
                        new DijkstrasSearch()
                                .addEdge(1, 2, 2.0)
                                .addEdge(2, 1, 1.0)
                                .addEdge(2, 3, 3.0)
                                .addEdge(3, 2, 0.0)
                                .addEdge(3, 4, 0.5)
                                .addEdge(4, 3, 5.0),
                        new SearchResult(1, 4, Arrays.asList(1, 2, 3, 4)),
                        new SearchResult(4, 1, Arrays.asList(4, 3, 2, 1)),
                        new SearchResult(1, 3, Arrays.asList(1, 2, 3)),
                        new SearchResult(3, 1, Arrays.asList(3, 2, 1)),
                        new SearchResult(1, 2, Arrays.asList(1, 2)),
                        new SearchResult(2, 1, Arrays.asList(2, 1)),
                        new SearchResult(2, 4, Arrays.asList(2, 3, 4)),
                        new SearchResult(4, 2, Arrays.asList(4, 3, 2)),
                        new SearchResult(2, 3, Arrays.asList(2, 3)),
                        new SearchResult(3, 2, Arrays.asList(3, 2))
                        // this is definitely enough for now!
                ),
                new GraphSearchTestCase("every node can reach itself without moving",
                        new DijkstrasSearch(/* empty! */),
                        new SearchResult(1, 1, List.of(1)),
                        new SearchResult(2, 2, List.of(2))
                        // you get the idea. We don't need to check for more of the same thing!
                ),
                new GraphSearchTestCase("search through a small undirected graph",
                        // taken from https://commons.wikimedia.org/wiki/File:Dijkstra_Animation.gif
                        new DijkstrasSearch()
                                .addUndirectedEdge(1, 2, 7.0)
                                .addUndirectedEdge(1, 3, 9.0)
                                .addUndirectedEdge(1, 6, 14.0)
                                .addUndirectedEdge(2, 3, 10.0)
                                .addUndirectedEdge(2, 4, 15.0)
                                .addUndirectedEdge(3, 6, 2.0)
                                .addUndirectedEdge(3, 4, 11.0)
                                .addUndirectedEdge(4, 5, 6.0)
                                .addUndirectedEdge(6, 5, 9.0),

                        new SearchResult(1, 5, List.of(1, 3, 6, 5)),
                        new SearchResult(5, 1, List.of(5, 6, 3, 1)),
                        new SearchResult(4, 2, List.of(4, 2)),
                        new SearchResult(5, 5, List.of(5)),
                        new SearchResult(6, 2, List.of(6, 3, 2)),
                        new SearchResult(4, 6, List.of(4, 3, 6))
                ),
        };
    }


    @ParameterizedTest
    @MethodSource("getReachEveryNodeTestCases")
    void canReachEveryNode(CanReachEveryNodeTestCase testCase) {
        boolean correctAnswer = testCase.canReachAllFromOne();
        boolean actualAnswer = testCase.graph().canReachEveryNode(1, testCase.allNodes());

        Assertions.assertEquals(correctAnswer, actualAnswer,
                String.format("%s: graph %s is actually %s", testCase.name(), testCase.graph(), correctAnswer ? "fully connected" : "NOT fully connected"));
    }

    static CanReachEveryNodeTestCase[] getReachEveryNodeTestCases() {
        return new CanReachEveryNodeTestCase[]{
                new CanReachEveryNodeTestCase("empty graph", new DijkstrasSearch(), Collections.emptySet(), false),
                new CanReachEveryNodeTestCase("singleton graph", new DijkstrasSearch(), Set.of(1), true),
                new CanReachEveryNodeTestCase("unconnected graph", new DijkstrasSearch(), Set.of(1, 2, 3, 4), false),
                new CanReachEveryNodeTestCase("straight line",
                        new DijkstrasSearch()
                                .addEdge(1, 2, 2.0)
                                .addEdge(2, 1, 1.0)
                                .addEdge(2, 3, 3.0)
                                .addEdge(3, 2, 0.0)
                                .addEdge(3, 4, 0.5)
                                .addEdge(4, 3, 5.0),
                        Set.of(1, 2, 3, 4),
                        true
                ),
                new CanReachEveryNodeTestCase("small graph",
                        // taken from https://commons.wikimedia.org/wiki/File:Dijkstra_Animation.gif
                        new DijkstrasSearch()
                                .addUndirectedEdge(1, 2, 7.0)
                                .addUndirectedEdge(1, 3, 9.0)
                                .addUndirectedEdge(1, 6, 14.0)
                                .addUndirectedEdge(2, 3, 10.0)
                                .addUndirectedEdge(2, 4, 15.0)
                                .addUndirectedEdge(3, 6, 2.0)
                                .addUndirectedEdge(3, 4, 11.0)
                                .addUndirectedEdge(4, 5, 6.0)
                                .addUndirectedEdge(6, 5, 9.0),
                        //    1
                        //   / \
                        //  2---3
                        //  |\ /|
                        //  5-4-6
                        Set.of(1, 2, 3, 4, 5, 6),
                        true
                ),

                new CanReachEveryNodeTestCase("small disjoint graph",
                        new DijkstrasSearch()
                                .addEdge(1, 2, 0.0)
                                .addEdge(3, 1, 1.0)
                                .addEdge(2, 3, 3.5)
                                .addUndirectedEdge(4, 5, 2.0)
                                .addUndirectedEdge(5, 6, 2.0),
                        // 1 --> 2  4-5-6
                        //  <-3<-/
                        Set.of(1, 2, 3, 4, 5, 6),
                        false
                )
        };
    }
}