package searches.answers;

import java.util.List;

// Used in tests
public record SearchResult(int start, int end, List<Integer> bestPath) {}
