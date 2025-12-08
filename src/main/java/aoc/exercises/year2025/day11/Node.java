package aoc.exercises.year2025.day11;

import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Node {
    private final Map<String, Integer> heuristic = new HashMap<>();
    private final String name;
    private final Map<String, Node> neighbors = new HashMap<>();

    public int getPathsTo(String otherNode, List<String> currentPath) {
        if (name.equals(otherNode)) {
            return 1;
        }

        if (heuristic.containsKey(otherNode)) {
            return heuristic.get(otherNode);
        }

        int countPaths = 0;
        for (Node neighbor : neighbors.values()) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add(name);
            countPaths += neighbor.getPathsTo(otherNode, newPath);
        }
        heuristic.putIfAbsent(otherNode, countPaths);
        return countPaths;
    }
}
