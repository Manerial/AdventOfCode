package aoc.exercises.year2025.day04;

import aoc.common_objects.*;
import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<Position, Roll> rolls = inputParser.parseInput();
        long initialSize = rolls.size();

        removeRolls(rolls);
        solution1 = initialSize - rolls.size();

        removeRollsUntilEnd(rolls);
        solution2 = initialSize - rolls.size();
    }

    /**
     * Remove the accessible rolls and all their occurrences in their neighbors
     *
     * @param rolls The map of rolls
     */
    private void removeRolls(Map<Position, Roll> rolls) {
        List<Roll> accessibleRolls = rolls.values().stream()
                .filter(Roll::isAccessible)
                .toList();

        accessibleRolls.forEach(roll -> roll.removeAllOccurrences(rolls));
    }

    /**
     * Remove the accessible rolls until no more can be removed
     *
     * @param rolls The map of rolls
     */
    private void removeRollsUntilEnd(Map<Position, Roll> rolls) {
        long currentRollSize;
        do {
            currentRollSize = rolls.size();
            removeRolls(rolls);
        } while (currentRollSize != rolls.size());
        removeRolls(rolls);
    }
}