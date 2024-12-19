package aoc.exercises.year2024.day19;

import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/19">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private List<String> availableTowels;
    private final Map<String, Long> waysToDo = new HashMap<>();
    private int minDesignSize;
    private int maxDesignSize;

    @Override
    public void run() {
        availableTowels = List.of(inputList.get(0).split(", "));
        minDesignSize = availableTowels.stream().mapToInt(String::length).min().orElseThrow();
        maxDesignSize = availableTowels.stream().mapToInt(String::length).max().orElseThrow();
        List<String> desiredDesigns = inputList.stream().skip(2).toList();

        List<Long> waysToDoEachDesign = desiredDesigns.stream()
                .map(this::computeWaysToDo)
                .toList();

        solution1 = waysToDoEachDesign.stream()
                .filter(ways -> ways > 0)
                .count();

        solution2 = waysToDoEachDesign.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private long computeWaysToDo(String design) {
        if (design.isEmpty()) {
            return 1;
        } else if (waysToDo.containsKey(design)) {
            return waysToDo.get(design);
        }
        long countDesigns = 0;
        int maxSizeCurrentTowel = Math.min(maxDesignSize, design.length());
        for (int size = minDesignSize; size <= maxSizeCurrentTowel; size++) {
            String currentTowel = design.substring(0, size);
            if (availableTowels.contains(currentTowel)) {
                String nextDesign = design.substring(size);
                countDesigns += computeWaysToDo(nextDesign);
            }
        }
        waysToDo.put(design, countDesigns);
        return countDesigns;
    }
}