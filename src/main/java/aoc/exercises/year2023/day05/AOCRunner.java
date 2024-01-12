package aoc.exercises.year2023.day05;

import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Almanach almanach = inputParser.parseInput();

        solution1 = almanach.seeds().stream()
                .mapToLong(value -> almanach.getSeedLocation(value).getLeft())
                .min()
                .orElse(0);

        long min = Long.MAX_VALUE;
        for (int seedIndex = 0; seedIndex < almanach.seeds().size(); seedIndex += 2) {
            long seedStart = almanach.seeds().get(seedIndex);
            long range = almanach.seeds().get(seedIndex + 1);
            long nextRange;
            for (long seed = seedStart; seed < seedStart + range; seed += nextRange) {
                Pair<Long, Long> value = almanach.getSeedLocation(seed);
                min = Math.min(min, value.getLeft());
                nextRange = Math.max(1, value.getRight());
            }
        }
        solution2 = min;
    }
}