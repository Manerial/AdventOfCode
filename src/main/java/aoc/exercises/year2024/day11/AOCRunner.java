package aoc.exercises.year2024.day11;

import utilities.AbstractAOC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Map<Long, Long> totalStones = new HashMap<>();

    @Override
    public void run() {
        totalStones = Arrays.stream(inputList.get(0).split(" "))
                .map(Long::parseLong)
                .collect(Collectors.groupingBy(Long::longValue, Collectors.counting()));

        for (int i = 0; i < 25; i++) {
            generateStones();
        }
        solution1 = countTotalStones();

        for (int i = 0; i < 50; i++) {
            generateStones();
        }
        solution2 = countTotalStones();
    }

    private void generateStones() {
        Map<Long, Long> newStones = new HashMap<>();
        Set<Map.Entry<Long, Long>> stones = totalStones.entrySet();
        for (Map.Entry<Long, Long> totalStone : stones) {
            fillWithGeneratedStones(newStones, totalStone.getKey(), totalStone.getValue());
        }
        totalStones = newStones;
    }

    private long countTotalStones() {
        return totalStones.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private void fillWithGeneratedStones(Map<Long, Long> newStones, long stone, long number) {
        // If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
        if (stone == 0) {
            newStones.put(1L, number);
            return;
        }

        // If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
        String stoneString = String.valueOf(stone);
        if (stoneString.length() % 2 == 0) {
            long leftHalf = Long.parseLong(stoneString.substring(0, stoneString.length() / 2));
            long rightHalf = Long.parseLong(stoneString.substring(stoneString.length() / 2));
            newStones.compute(leftHalf, (k, v) -> v == null ? number : v + number);
            newStones.compute(rightHalf, (k, v) -> v == null ? number : v + number);
            return;
        }

        // If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
        newStones.compute(stone * 2024, (k, v) -> v == null ? number : v + number);
    }
}