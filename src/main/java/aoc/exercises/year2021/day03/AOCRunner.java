package aoc.exercises.year2021.day03;

import utilities.AbstractAOC;
import utilities.ArrayFill;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private record RecordEpsilonGamma(int gamma, int epsilon) {
    }

    @Override
    public void run() {
        Integer[] countOnesByPosition = countOnesInInput();

        RecordEpsilonGamma recordEpsilonGamma = computeEpsilonGamma(countOnesByPosition);
        solution1 = (recordEpsilonGamma.gamma() * recordEpsilonGamma.epsilon());

        String binaryO2Rate = extractCharacterRateRecursive(inputList, 0, true);
        String binaryCO2Rate = extractCharacterRateRecursive(inputList, 0, false);

        int co2Rate = Integer.parseInt(binaryCO2Rate, 2);
        int o2Rate = Integer.parseInt(binaryO2Rate, 2);

        solution2 = (o2Rate * co2Rate);
    }

    /**
     * <pre>
     *  For an array of string, count the number of '1' for each position.
     *  Example :
     *  010101
     *  111111
     *  001101
     *  => [1, 2, 2, 3, 1, 3]
     * </pre>
     */
    private Integer[] countOnesInInput() {
        int size = inputList.get(0).length();
        Integer[] countOnesByPosition = ArrayFill.initIntArray(size, 0);
        inputList.forEach(line -> countOnesInLine(countOnesByPosition, line));
        return countOnesByPosition;
    }

    private static void countOnesInLine(Integer[] countOnesByPosition, String line) {
        AtomicInteger position = new AtomicInteger();
        line.chars().forEach(binaryChar -> {
            int binaryValue = Character.getNumericValue(binaryChar);
            countOnesByPosition[position.getAndIncrement()] += binaryValue;
        });
    }

    /**
     * <pre>
     * Compute the epsilon and gamma values of the input.
     * Epsilon : the most present character between '0' and '1' at a position
     * Gamma : the least present character between '0' and '1' at a position
     * </pre>
     *
     * @param countOnesByPosition : The sum of all '1' for a specific position
     * @return a record containing the epsilon and gamma
     */
    private RecordEpsilonGamma computeEpsilonGamma(Integer[] countOnesByPosition) {
        StringBuilder gammaStr = new StringBuilder();
        StringBuilder epsilonStr = new StringBuilder();
        int average = inputList.size() / 2;

        for (Integer frequency : countOnesByPosition) {
            if (frequency > average) {
                gammaStr.append("1");
                epsilonStr.append("0");
            } else {
                gammaStr.append("0");
                epsilonStr.append("1");
            }
        }

        int gamma = Integer.parseInt(gammaStr.toString(), 2);
        int epsilon = Integer.parseInt(epsilonStr.toString(), 2);

        return new RecordEpsilonGamma(gamma, epsilon);
    }

    /**
     * <pre>
     * Find the most/least common characters recursively.
     * Starts by finding the most/least common char at position 1, then filter the list on it each string that has this char at position 1.
     * Then find the most/least common char at position 2, filter the list on it, etc.
     * </pre>
     *
     * @param list       : The list of string to filter
     * @param position      : The current position of the character to filter on
     * @param mostCommon : set if we are looking for the most (true) or least (false) common character
     * @return the string containing the most/the least common character recursively
     */
    private String extractCharacterRateRecursive(List<String> list, int position, boolean mostCommon) {
        if (list.size() <= 1) {
            return list.get(0);
        }
        char charToFilterOn = getCharToFilterOn(list, position, mostCommon);
        List<String> filteredList = filterOnCharAtPosition(list, position, charToFilterOn);
        return extractCharacterRateRecursive(filteredList, position + 1, mostCommon);
    }

    private static char getCharToFilterOn(List<String> list, int index, boolean mostCommon) {
        long numberOf0 = countNumberOf0AtPosition(list, index);
        long numberOf1 = list.size() - numberOf0;

        char nextChar;
        if (numberOf0 > numberOf1) {
            nextChar = mostCommon ? '0' : '1';
        } else {
            nextChar = mostCommon ? '1' : '0';
        }
        return nextChar;
    }

    private static long countNumberOf0AtPosition(List<String> list, int index) {
        return list.stream()
                .filter(e -> e.charAt(index) == '0')
                .count();
    }

    private static List<String> filterOnCharAtPosition(List<String> list, int index, char nextChar) {
        return list.stream()
                .filter(e -> e.charAt(index) == nextChar)
                .toList();
    }
}
