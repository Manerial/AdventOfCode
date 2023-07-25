package aoc.exercises.year2022.day01;

import aoc.common_objects.SortedLimitedList;
import utilities.AbstractAOC;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static utilities.ResourceIO.DELIMITER;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> backpacks = createBackpacks(inputList).stream()
                .toList();
        solution1 = backpacks.getFirst();
        solution2 = backpacks.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Integer> createBackpacks(List<String> list) {
        String baseInput = String.join(DELIMITER, list);
        String[] backpacksStr = baseInput.split(DELIMITER + DELIMITER);

        List<Integer> backpacks = new SortedLimitedList<>(Collections.reverseOrder(), 3);
        backpacks.addAll(getBackpacks(backpacksStr));
        return backpacks;
    }

    private List<Integer> getBackpacks(String[] backpacksStr) {
        return Stream.of(backpacksStr)
                .map(this::getBackpack)
                .toList();
    }

    private int getBackpack(String backpack) {
        return Stream.of(backpack.split(DELIMITER))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}