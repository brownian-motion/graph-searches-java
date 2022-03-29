package searches.answers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import searches.AStarDistanceEstimator;
import searches.GraphSearchTestCase;
import searches.SearchResult;

import java.util.*;

public class AStarSearch_AnswerKeyTest {

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
                new GraphSearchTestCase("empty graph always returns an empty list or null", new AStarSearch_AnswerKey((a, b) -> Double.POSITIVE_INFINITY),
                        new SearchResult(1, 2, Collections.emptyList()),
                        new SearchResult(2, 1, Collections.emptyList()),
                        new SearchResult(5, 1, Collections.emptyList())
                ),
                new GraphSearchTestCase("straight line",
                        new AStarSearch_AnswerKey((a, b) -> 0.0 /* we must always underestimate */)
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
                        new AStarSearch_AnswerKey((a, b) -> Double.POSITIVE_INFINITY),
                        new SearchResult(1, 1, List.of(1)),
                        new SearchResult(2, 2, List.of(2))
                        // you get the idea. We don't need to check for more of the same thing!
                ),
                new GraphSearchTestCase("search through a small undirected graph",
                        // taken from https://commons.wikimedia.org/wiki/File:Dijkstra_Animation.gif
                        new AStarSearch_AnswerKey((a, b) -> Math.abs(b - a))
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
                new GraphSearchTestCase("search through a small undirected two-path graph",
                        // taken from https://upload.wikimedia.org/wikipedia/commons/9/98/AstarExampleEn.gif
                        new AStarSearch_AnswerKey(new AStarDistanceEstimator() {
                            final Map<Integer, Double> estimatesToNodeTen = new HashMap<>();

                            {
                                estimatesToNodeTen.put(0, 6.0);
                                estimatesToNodeTen.put(1, 4.0);
                                estimatesToNodeTen.put(2, 2.0);
                                estimatesToNodeTen.put(3, 4.0);
                                estimatesToNodeTen.put(4, 4.5);
                                estimatesToNodeTen.put(5, 2.0);
                                estimatesToNodeTen.put(10, 0.0);
                            }

                            @Override
                            public double estimateCostToEnd(int source, int goal) {
                                assert goal == 10;
                                return estimatesToNodeTen.get(source);
                            }
                        })
                                .addUndirectedEdge(0, 1, 1.5)
                                .addUndirectedEdge(1, 2, 2.0)
                                .addUndirectedEdge(2, 3, 3.0)
                                .addUndirectedEdge(3, 10, 4.0)
                                .addUndirectedEdge(0, 4, 2.0)
                                .addUndirectedEdge(4, 5, 3.0)
                                .addUndirectedEdge(5, 10, 2.0),

                        new SearchResult(0, 10, List.of(0, 4, 5, 10))
                ),
        };
    }
}