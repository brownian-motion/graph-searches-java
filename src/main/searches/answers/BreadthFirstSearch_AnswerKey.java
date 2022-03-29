package searches.answers;

import searches.BreadthFirstSearch;

import java.util.*;

public class BreadthFirstSearch_AnswerKey extends BreadthFirstSearch {

    // this method only exists so that I can write tests super easily
    @Override
    public BreadthFirstSearch_AnswerKey addEdge(int source, int neighbor) {
        super.addEdge(source, neighbor);
        return this;
    }

    public List<Integer> breadthFirstSearch(int source, int dest) {
        Queue<Integer> frontier = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> shortestPath = new HashMap<>(); // neighbor -> fastest origin

        frontier.add(source);

        while (!frontier.isEmpty()) {
            int currNode = frontier.remove();
            if (visited.contains(currNode)) continue;

            if (currNode == dest) {
                return reconstructShortestPath(source, dest, shortestPath);
            }

            visited.add(currNode);
            var neighbors = this.getNeighbors(currNode);
            for (int neighbor : neighbors) {
                // if we visited it, we don't need to re-compute a path to it again
                if (visited.contains(neighbor)) continue;

                // If it's in the queue to visit soon, we've already computed the shortest path to it
                // so we don't need to re-compute a path to it again.
                // Everything that is in the frontier is also a key in the shortestPath map,
                // so I'm using the O(1) HashMap.containsKey() hash lookup instead of the O(n) Queue.contains() linear search.
                if (shortestPath.containsKey(neighbor)) continue;

                // now that the edge cases are out of the way, let's do the work:

                // record the fastest way to get to the neighbor, so we can piece together the shortest path later
                shortestPath.put(neighbor, currNode);
                frontier.add(neighbor);
            }
        }

        // if we made it this far, then we KNOW that we must have tried every route possible and still couldn't reach the end,
        // because if we reached the destination we would have returned already. So there must be no path!
        return Collections.emptyList();
    }

    private List<Integer> reconstructShortestPath(int source, int dest, Map<Integer, Integer> shortestPathSegments) {
        // I want to be able to efficiently insert into the front, but that's hard to write cleanly and I want to demonstrate it clearly.
        // So here I'm just using an inefficient algorithm to show a simple series of steps, since this part isn't super important.

        var actualShortestPath = new ArrayDeque<Integer>(); // this type lets me insert at the front mostly quickly, but an arraylist is fine too
        for (int currNode = dest; currNode != source; currNode = shortestPathSegments.get(currNode)) {
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
            Set<Integer> neighbors = this.getNeighbors(currNode);

            Set<Integer> unvisitedNeighbors = new HashSet<>(neighbors);
            unvisitedNeighbors.removeAll(visited);

            frontier.addAll(unvisitedNeighbors);
        }

        return visited.equals(allNodes);
    }
}
