package aoc.exercises.year2025.day05;

import org.apache.commons.lang3.tuple.*;
import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Pair<List<LongRange>, List<Long>> pair = inputParser.parseInput();

        List<LongRange> ranges = pair.getLeft();
        List<Long> numbers = pair.getRight();

        solution1 = numbers.stream()
                .filter(number -> ranges.stream().anyMatch(range -> range.contains(number)))
                .count();

        reduceRanges(ranges);
        solution2 = ranges.stream().mapToLong(LongRange::interval).sum();
    }

    /**
     * <p>
     * Sort a list of ranges to reduce them if their are overlaping.</br>
     * Example :</br>
     * -initial state> [9-50, 5-20, 0-10, 6-7]</br>
     * -sort> [0-10, 5-20, 6-7, 9-50]</br>
     * -reduce step1> [<b>0-10</b>, 11-20, 0-(-1), 11-50]</br>
     * -reduce step2> [0-10, <b>11-20</b>, 0-(-1), 21-50]</br>
     * </p>
     *
     * @param ranges the ranges to reduce to their minimum
     */
    private static void reduceRanges(List<LongRange> ranges) {
        ranges.sort(Comparator.comparingLong(LongRange::getMin));
        // Reduce
        for (int i = 0; i < ranges.size() - 1; i++) {
            LongRange currentRange = ranges.get(i);
            ranges.subList(i + 1, ranges.size()).stream()
                    .filter(nextRange -> currentRange.getMax() >= nextRange.getMin())
                    .forEach(nextRange -> {
                        nextRange.setMin(currentRange.getMax() + 1);
                        if (currentRange.getMax() >= nextRange.getMax()) {
                            nextRange.reduceIntervalTo0();
                        }
                    });
        }
    }
}