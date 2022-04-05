package searches.answers;

import graphs.GraphSearch;
import graphs.ReachabilitySearch;
import graphs.WeightedEdge;
import graphs.WeightedGraph;
import searches.AStarDistanceEstimator;

import java.util.*;
import java.util.stream.Collectors;

public record AStarSearch_AnswerKey(AStarDistanceEstimator estimator,
                                    WeightedGraph<?> graph) implements GraphSearch, ReachabilitySearch {

    @Override
    public List<Integer> findShortestPath(int source, int dest) {
        return aStarSearch(source, dest);
    }

    private record PathToNode(int bestSource, double totalCostToPoint) {}

    public List<Integer> aStarSearch(int source, int dest) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, PathToNode> bestPathSegments = new HashMap<>(); // neighbor -> fastest origin
        PriorityQueue<Integer> frontier = new PriorityQueue<>(Comparator.comparingDouble(
                // we always want to prioritize paths with the shortest total cost so far,
                // because then we can assume that once we've visited a node,
                // we've already explored every shortest path to get there
                // and we can just ignore all subsequent paths.
                node -> bestPathSegments.get(node).totalCostToPoint + estimator.estimateCostToEnd(node, dest) // <-- this is one of the few changes from Dijkstra's
        ));

        frontier.add(source);
        bestPathSegments.put(source, new PathToNode(source /* not actually used */, 0));

        while (!frontier.isEmpty()) {
            int currNode = frontier.remove();
            if (visited.contains(currNode)) continue;

            if (currNode == dest) {
                return reconstructShortestPath(source, dest, bestPathSegments);
            }

            double costToCurrNode = bestPathSegments.get(currNode).totalCostToPoint;

            visited.add(currNode);
            var edges = graph.getNeighbors(currNode);
            for (WeightedEdge edge : edges) {
                int neighbor = edge.neighbor();

                // if we visited it, we've already used the shortest-length path to get there,
                // so we don't need to bother exploring these longer paths to get there
                if (visited.contains(neighbor)) continue;

                // Otherwise, the shortest path we've seen so far might not have been the cheapest path to get there,
                // so we always need to re-compute the path we're exploring now and update it if necessary.

                double costToNeighbor = costToCurrNode + edge.cost();
                double estimatedTotalCostUsingNeighbor = costToNeighbor + estimator.estimateCostToEnd(neighbor, dest); // <-- this is one of the few changes from Dijkstra's
                PathToNode shortestPathToNeighbor = bestPathSegments.get(neighbor);
                if (shortestPathToNeighbor == null || costToNeighbor < shortestPathToNeighbor.totalCostToPoint) {
                    // then this is our first time encountering it, or we found a faster way there.

                    // record the new fastest way to get to the neighbor, so we can piece together the shortest path later
                    bestPathSegments.put(neighbor, new PathToNode(currNode, costToNeighbor));

                    // we changed this node's priority by marking it closer,
                    // so we need to make the priority queue move it into the right position:
                    decreasePriority(frontier, neighbor);
                }

                // Everything that is in the frontier is also a key in the shortestPath map,
                // so I'm using the O(1) HashMap.containsKey() hash lookup instead of the O(n) Queue.contains() linear search.
                if (bestPathSegments.containsKey(neighbor)) continue; // we don't want duplicates in the frontier
                frontier.add(neighbor);
            }
        }

        // if we made it this far, then we KNOW that we must have tried every route possible and still couldn't reach the end,
        // because if we reached the destination we would have returned already. So there must be no path!
        return Collections.emptyList();
    }

    private void decreasePriority(PriorityQueue<Integer> queue, int node) {
        // we're tracking the priority in a map stored outside the queue that only changes
        // at the same time that we insert or decrease priority,
        // so we just need to make it recompute where to put and leave this node:
        queue.remove(node);
        queue.add(node);
    }

    private List<Integer> reconstructShortestPath(int source, int dest, Map<Integer, PathToNode> shortestPathSegments) {
        // I want to be able to efficiently insert into the front, but that's hard to write cleanly and I want to demonstrate it clearly.
        // So here I'm just using an inefficient algorithm to show a simple series of steps, since this part isn't super important.

        var actualShortestPath = new ArrayDeque<Integer>(); // this type lets me insert at the front mostly quickly, but an arraylist is fine too
        for (int currNode = dest; currNode != source; currNode = shortestPathSegments.get(currNode).bestSource) {
            actualShortestPath.addFirst(currNode);
        }
        actualShortestPath.addFirst(source);

        // I want a simple list arranged efficiently, and this accomplishes that:
        return new ArrayList<>(actualShortestPath);
    }

    public boolean canReachEveryNode(int source, Set<Integer> allNodes) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> frontier = new ArrayDeque<>();
        frontier.add(source);
        while (!frontier.isEmpty()) {
            int currNode = frontier.remove();
            visited.add(currNode);

            Set<Integer> unvisitedNeighbors = graph.getNeighbors(currNode).stream()
                    .map(WeightedEdge::neighbor)
                    .collect(Collectors.toSet());
            unvisitedNeighbors.removeAll(visited);

            frontier.addAll(unvisitedNeighbors);
        }

        return visited.equals(allNodes);
    }
}
