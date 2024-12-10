package aoc.exercises.year2024.day10;

import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trails {
    private final Map<Position, List<List<Position>>> allPathsToTop = new HashMap<>();

    public void addPath(List<Position> newPath, Position finalPosition) {
        List<List<Position>> pathsToTop = allPathsToTop.getOrDefault(finalPosition, new ArrayList<>());
        pathsToTop.add(newPath);
        allPathsToTop.put(finalPosition, pathsToTop);
    }

    public long countTrails() {
        return allPathsToTop.values().stream().mapToInt(List::size).sum();
    }

    public long countDistinctTrails() {
        return allPathsToTop.size();
    }
}
