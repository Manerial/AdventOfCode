package aoc.exercises.year2025.day02;

import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private record LongRange(long min, long max) {
        public static LongRange toRange(String string, String splitter) {
            String[] splits = string.split(splitter);
            return new LongRange(Long.parseLong(splits[0]), Long.parseLong(splits[1]));
        }

        public boolean contains(long number) {
            return number >= min && number <= max;
        }
    }

    @Override
    public void run() {
        // We assume that each range covers a unique set of IDs
        List<LongRange> ranges = Arrays.stream(inputList.getFirst().split(","))
                .map(string -> LongRange.toRange(string, "-"))
                .toList();

        Set<Long> invalidIds = getInvalidIds(ranges);

        Set<Long> invalidIds1 = new HashSet<>();
        Set<Long> invalidIds2 = new HashSet<>();
        invalidIds.forEach(invalidId ->
                ranges.stream()
                        .filter(range -> range.contains(invalidId))
                        .findAny()
                        .ifPresent(r -> {
                            invalidIds2.add(invalidId);
                            // First and second part are the same ? -> exercise 1
                            long[] split = splitIn2(invalidId);
                            if (split[0] == split[1]) {
                                invalidIds1.add(invalidId);
                            }
                        })
        );

        solution1 = invalidIds1.stream().mapToLong(Long::longValue).sum();
        solution2 = invalidIds2.stream().mapToLong(Long::longValue).sum();
    }

    /**
     * Get a list of all invalid IDs within a range.
     * An ID is constructed as a concatenation of multiple times the same set of digits.
     * Example: 222222, 949494, 12361236
     *
     * @param ranges the list of ranges
     * @return a set of all invalid numbers
     */
    private static Set<Long> getInvalidIds(List<LongRange> ranges) {
        long maxInRange = ranges.stream()
                .mapToLong(LongRange::max)
                .max()
                .orElseThrow();

        long[] splitMax = splitIn2(maxInRange);
        long maxPart = splitMax[0];

        Set<Long> invalidIds = new HashSet<>();
        for (long part = 1; part <= maxPart; part++) {
            int nbDigitsInPart = (int) Math.floor(Math.log10(part)) + 1; // Contains the offset of the next part. 1000 -> 4, 21 -> 2
            long idToCheck = part;
            while (idToCheck <= maxInRange) {
                // Concatenate a new part at the end of idToCheck (if idToCheck=21 and part=21, then idToCheck=2121)
                idToCheck = (long) (idToCheck * Math.pow(10, nbDigitsInPart) + part);
                invalidIds.add(idToCheck);
            }
        }
        return invalidIds;
    }

    /**
     * Split a given number in two, in the middle of the number.
     * Example :
     * 9494978970 -> 94949 and 78970
     *
     * @param number the number to split
     * @return the two parts
     */
    private static long[] splitIn2(long number) {
        long[] result = new long[2];
        double digitsToKeep = Math.pow(10, Math.floor((Math.log10(number) + 1) / 2)); // 100000
        result[0] = (long) (number / digitsToKeep); // 94949
        result[1] = (long) (number % digitsToKeep); // 78970
        return result;
    }
}