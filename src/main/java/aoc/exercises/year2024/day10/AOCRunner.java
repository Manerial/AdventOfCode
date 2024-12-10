package aoc.exercises.year2024.day10;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Map<Position, Integer> topographicMap;


    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        topographicMap = inputParser.parseInput();
        List<Trails> trails = getPathsValues();
        solution1 = trails.stream().mapToLong(Trails::countDistinctTrails).sum();
        solution2 = trails.stream().mapToLong(Trails::countTrails).sum();
    }

    private List<Trails> getPathsValues() {
        Set<Position> startingTrail = topographicMap.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        return startingTrail.stream()
                .map(start -> {
                    Trails trails = new Trails();
                    getReachableTop(topographicMap, new ArrayList<>(List.of(start)), trails);
                    return trails;
                })
                .toList();
    }

    private void getReachableTop(Map<Position, Integer> topographicMap, List<Position> currentPath, Trails trails) {
        Position currentPosition = currentPath.get(currentPath.size() - 1);
        Integer currentValue = topographicMap.get(currentPosition);
        if (currentValue == 9) {
            List<Position> newPath = new ArrayList<>(currentPath);
            newPath.add(currentPosition);
            trails.addPath(newPath, currentPosition);
            return;
        }
        List<Position> accessibleNeighbors = currentPosition.getDirectNeighbors().stream()
                .filter(topographicMap::containsKey)
                .filter(neighbor -> topographicMap.get(neighbor) == currentValue + 1)
                .filter(neighbor -> !currentPath.contains(neighbor))
                .toList();

        accessibleNeighbors
                .forEach(neighbor -> {
                    List<Position> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    getReachableTop(topographicMap, newPath, trails);
                });
    }
}