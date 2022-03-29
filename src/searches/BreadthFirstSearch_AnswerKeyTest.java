package searches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BreadthFirstSearch_AnswerKeyTest {

    @ParameterizedTest
    @MethodSource("getBreadthFirstSearches")
    public void BreadthFirstSearch_AnswerKey(BreadthFirstSearchTestCase testCase) {
        for (var search : testCase.searches()) {
            List<Integer> actualPath = testCase.graph().breadthFirstSearch(search.start, search.end);

            if (search.bestPath == null || search.bestPath.isEmpty()) {
                Assertions.assertTrue(actualPath == null || actualPath.isEmpty(), String.format("%s: search for %d -> %d through %s should have failed, but yielded %s",
                        testCase.name, search.start, search.end, testCase.graph, actualPath));
            } else {
                Assertions.assertEquals(search.bestPath, actualPath,
                        String.format("%s: search for %d -> %d through %s yielded wrong path",
                                testCase.name, search.start, search.end, testCase.graph));
            }
        }
    }

    record Search(int start, int end, List<Integer> bestPath) {}

    record BreadthFirstSearchTestCase(String name, BreadthFirstSearch_AnswerKey graph, Search... searches) {}

    static BreadthFirstSearchTestCase[] getBreadthFirstSearches() {
        return new BreadthFirstSearchTestCase[]{
                new BreadthFirstSearchTestCase("empty graph always returns an empty list or null", new BreadthFirstSearch_AnswerKey(),
                        new Search(1, 2, Collections.emptyList()),
                        new Search(2, 1, Collections.emptyList()),
                        new Search(5, 1, Collections.emptyList())
                ),
                new BreadthFirstSearchTestCase("straight line",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(3, 4),
                        new Search(1, 4, Arrays.asList(1, 2, 3, 4)),
                        new Search(4, 1, Arrays.asList(4, 3, 2, 1)),
                        new Search(1, 3, Arrays.asList(1, 2, 3)),
                        new Search(3, 1, Arrays.asList(3, 2, 1)),
                        new Search(1, 2, Arrays.asList(1, 2)),
                        new Search(2, 1, Arrays.asList(2, 1)),
                        new Search(2, 4, Arrays.asList(2, 3, 4)),
                        new Search(4, 2, Arrays.asList(4, 3, 2)),
                        new Search(2, 3, Arrays.asList(2, 3)),
                        new Search(3, 2, Arrays.asList(3, 2))
                        // this is definitely enough for now!
                ),
                new BreadthFirstSearchTestCase("every node can reach itself without moving",
                        new BreadthFirstSearch_AnswerKey(/* empty! */),
                        new Search(1, 1, List.of(1)),
                        new Search(2, 2, List.of(2))
                        // you get the idea. We don't need to check for more of the same thing!
                ),
                new BreadthFirstSearchTestCase("search through a small graph",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(1, 3)
                                .addEdge(2, 3)
                                .addEdge(2, 4)
                                .addEdge(3, 4)
                                .addEdge(2, 5)
                                .addEdge(4, 5)
                                .addEdge(3, 6)
                                .addEdge(5, 6),
                        //    1
                        //   / \
                        //  2---3
                        //  |\ /|
                        //  5-4-6
                        new Search(1, 6, List.of(1, 3, 6)),
                        new Search(5, 1, List.of(5, 2, 1)),
                        new Search(4, 2, List.of(4, 2)),
                        new Search(5, 5, List.of(5)),
                        new Search(6, 2, List.of(6, 3, 2) /* could also be 6->4->2 */)
                ),

                new BreadthFirstSearchTestCase("search through a medium graph",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(1, 3)
                                .addEdge(2, 3)
                                .addEdge(2, 4)
                                .addEdge(3, 4)
                                .addEdge(2, 5)
                                .addEdge(4, 5)
                                .addEdge(3, 6)
                                .addEdge(5, 6)
                                .addEdge(5, 7)
                                .addEdge(3, 7),
                        //    1
                        //   / \
                        //  2---3
                        //  |\ /|
                        //  | 4 |
                        //  |/ /|
                        //  5-7 6
                        new Search(1, 6, List.of(1, 3, 6)),
                        new Search(5, 1, List.of(5, 2, 1)),
                        new Search(4, 2, List.of(4, 2)),
                        new Search(5, 5, List.of(5)),
                        new Search(6, 2, List.of(6, 3, 2) /* could also be 6->4->2 */),
                        new Search(1, 7, List.of(1, 3, 7)),
                        new Search(7, 1, List.of(7, 3, 1))
                ),
        };
    }


    record CanReachEveryNodeTestCase(String name, BreadthFirstSearch_AnswerKey graph, Set<Integer> allNodes,
                                     boolean canReachAllFromOne) {}

    @ParameterizedTest
    @MethodSource("getReachEveryNodeTestCases")
    void canReachEveryNode(CanReachEveryNodeTestCase testCase) {
        boolean correctAnswer = testCase.canReachAllFromOne;
        boolean actualAnswer = testCase.graph.canReachEveryNode(1, testCase.allNodes);

        Assertions.assertEquals(correctAnswer, actualAnswer,
                String.format("%s: graph %s is actually %s", testCase.name, testCase.graph, correctAnswer ? "fully connected" : "NOT fully connected"));
    }

    static CanReachEveryNodeTestCase[] getReachEveryNodeTestCases() {
        return new CanReachEveryNodeTestCase[]{
                new CanReachEveryNodeTestCase("empty graph", new BreadthFirstSearch_AnswerKey(), Collections.emptySet(), false),
                new CanReachEveryNodeTestCase("singleton graph", new BreadthFirstSearch_AnswerKey(), Set.of(1), true),
                new CanReachEveryNodeTestCase("unconnected graph", new BreadthFirstSearch_AnswerKey(), Set.of(1, 2, 3, 4), false),
                new CanReachEveryNodeTestCase("straight line",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(2, 3)
                                .addEdge(3, 4),
                        Set.of(1, 2, 3, 4),
                        true
                ),
                new CanReachEveryNodeTestCase("small graph",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(1, 3)
                                .addEdge(2, 3)
                                .addEdge(2, 4)
                                .addEdge(3, 4)
                                .addEdge(2, 5)
                                .addEdge(4, 5)
                                .addEdge(3, 6)
                                .addEdge(5, 6),
                        //    1
                        //   / \
                        //  2---3
                        //  |\ /|
                        //  5-4-6
                        Set.of(1, 2, 3, 4, 5, 6),
                        true
                ),

                new CanReachEveryNodeTestCase("medium interconnected graph",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(1, 3)
                                .addEdge(2, 3)
                                .addEdge(2, 4)
                                .addEdge(3, 4)
                                .addEdge(2, 5)
                                .addEdge(4, 5)
                                .addEdge(3, 6)
                                .addEdge(5, 6)
                                .addEdge(5, 7)
                                .addEdge(3, 7),
                        //    1
                        //   / \
                        //  2---3
                        //  |\ /|
                        //  | 4 |
                        //  |/ /|
                        //  5-7 6
                        Set.of(1, 2, 3, 4, 5, 6, 7),
                        true
                ),

                new CanReachEveryNodeTestCase("small disjoint graph",
                        new BreadthFirstSearch_AnswerKey()
                                .addEdge(1, 2)
                                .addEdge(1, 3)
                                .addEdge(2, 3)
                                .addEdge(4, 5)
                                .addEdge(5, 6),
                        // 1 --- 2  4-5-6
                        //  \-3-/
                        Set.of(1, 2, 3, 4, 5, 6),
                        false
                )
        };
    }
}