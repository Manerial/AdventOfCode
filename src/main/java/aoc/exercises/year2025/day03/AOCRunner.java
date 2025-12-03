package aoc.exercises.year2025.day03;

import org.apache.commons.lang3.tuple.*;
import utilities.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        solution1 = inputList.stream()
                .mapToLong(line -> this.getLargestBatteries(line, 2))
                .sum();
        solution2 = inputList.stream()
                .mapToLong(line -> this.getLargestBatteries(line, 12))
                .sum();
    }

    /**
     * Get the maximum number that can be built with a string of numbers (ex : "1234") by keeping a certain number of digits (batterySize)
     * ex : line = "234234234234278", batterySize = 12
     * then return 434234234278
     *
     * @param line        the line of numbers
     * @param batterySize the number of digits to keep
     * @return the maximum number that can be built.
     */
    private long getLargestBatteries(String line, int batterySize) {
        StringBuilder resultStr = new StringBuilder();
        int currentIndex = 0;
        while (resultStr.length() != batterySize) {
            if (resultStr.length() + line.length() - currentIndex == batterySize) {
                resultStr.append(line.substring(currentIndex));
                break;
            }

            String subline = line.substring(currentIndex);
            // subStringLength is the maximum set of char we can treat without eating the final char set
            // line.length - currentIndex - substringLength = batterySize - resultStr.length + 1
            // substringLength = -(batterySize - resultStr.length - line.length + currentIndex - 1)
            int subStringLength = line.length() + resultStr.length() - currentIndex - batterySize + 1;
            if (subline.length() > subStringLength) {
                subline = subline.substring(0, subStringLength);
            }
            Pair<Character, Integer> maxCharIndex = getMaxCharIndex(subline);
            resultStr.append(maxCharIndex.getKey());
            currentIndex += maxCharIndex.getValue() + 1;
        }
        return Long.parseLong(resultStr.toString());
    }

    /**
     * Return the max char (0-9) of a string and it's index
     *
     * @param string the string to parse
     * @return a pair containing the max char and it's index in the line
     */
    private Pair<Character, Integer> getMaxCharIndex(String string) {
        char max = (char) string.chars().max().orElseThrow();
        int index = string.indexOf(max);
        return new ImmutablePair<>(max, index);
    }
}