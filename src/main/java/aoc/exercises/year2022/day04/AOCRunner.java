package aoc.exercises.year2022.day04;

import utilities.AbstractAOC;
import aoc.common_objects.Range;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int contains = 0;
        int overlap = 0;
        for (String string : inputList) {
            // We get our ranges
            Range range1 = getRange(string.split(",")[0]);
            Range range2 = getRange(string.split(",")[1]);
            // If one contains the other, ++
            if (range1.contains(range2) || range2.contains(range1)) {
                contains++;
            }
            // If one overlap the other, ++
            if (range1.isOverlappedBy(range2) || range2.isOverlappedBy(range1)) {
                overlap++;
            }
        }
        solution1 = contains;
        solution2 = overlap;
    }

    private Range getRange(String str) {
        int borne1 = Integer.parseInt(str.split("-")[0]);
        int borne2 = Integer.parseInt(str.split("-")[1]);
        return new Range(borne1, borne2);
    }

}
