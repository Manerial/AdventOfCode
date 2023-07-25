package aoc.exercises.year2022.day01;

import utilities.AbstractAOC;

import java.util.ArrayList;
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
    private List<Integer> backpacks = new ArrayList<>();

    @Override
    public void run() {
        createBackPacks(inputList);
        Collections.sort(backpacks);
        solution1 = backpacks.get(backpacks.size() - 1);
        solution2 = backpacks.subList(backpacks.size() - 3, backpacks.size()).stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private void createBackPacks(List<String> list) {
        String baseInput = String.join(DELIMITER, list);
        String[] backpacksStr = baseInput.split(DELIMITER + DELIMITER);

        backpacks = new ArrayList<>(Stream.of(backpacksStr)
                .map(backpack -> Stream.of(backpack.split(DELIMITER))
                        .mapToInt(Integer::parseInt)
                        .sum()
                )
                .toList());
    }
}