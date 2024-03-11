package aoc.exercises.year2020.day09;

import aoc.common_objects.RoundDeque;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private int preamble;
    private List<Long> numbers;

    @Override
    public void run() {
        init();

        long firstUnreachableNumber = getFirstUnreachableNumber();
        solution1 = firstUnreachableNumber;

        List<Long> range = findSequenceUpTo(firstUnreachableNumber);
        long min = range.stream().mapToInt(Long::intValue).min().orElseThrow();
        long max = range.stream().mapToInt(Long::intValue).max().orElseThrow();
        solution2 = min + max;
    }

    private void init() {
        if (isExample) {
            preamble = 5;
        } else {
            preamble = 25;
        }
        numbers = new ArrayList<>();
        for (String line : inputList) {
            numbers.add(Long.parseLong(line));
        }
    }

    /**
     * Get the first number that can't be reached by summing two of the N previous numbers
     * where N is the preamble we got in {@link #init()}.
     *
     * @return the first number that can't be reached by summing two of the N previous numbers
     */
    private long getFirstUnreachableNumber() {
        // Get the first N numbers of the list, where N = preamble
        RoundDeque<Long> previousNumbers = new RoundDeque<>();
        previousNumbers.addAll(numbers.subList(0, preamble));

        for (int i = preamble; i < numbers.size(); i++) {
            long currentNumber = numbers.get(i);
            if (!isReachable(previousNumbers, currentNumber)) {
                return currentNumber;
            }
            previousNumbers.removeFirst();
            previousNumbers.addLast(currentNumber);
        }
        throw new NoSuchElementException();
    }

    /**
     * Check if a number can be reached by summing two of the N previous numbers
     *
     * @param previousNumbers : the list of previous numbers to use for the check
     * @param currentNumber   : the number to check if it can be reached
     * @return true if the number can be reached by summing two of the N previous numbers, false otherwise
     */
    private boolean isReachable(RoundDeque<Long> previousNumbers, long currentNumber) {
        List<Long> previousNumbersCopy = new ArrayList<>(previousNumbers);
        for (int i = 0; i < previousNumbersCopy.size(); i++) {
            long previousNumber = previousNumbersCopy.get(i);
            for (int j = 0; j < previousNumbersCopy.size(); j++) {
                if (i == j) {
                    continue;
                }
                long nextNumber = previousNumbersCopy.get(j);
                if (previousNumber + nextNumber == currentNumber) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find the sequence of numbers that can be sum up to the given number.
     *
     * @param number : the number to reach by summing any sequence of numbers
     * @return the sequence of numbers that can be sum up to the given number
     */
    private List<Long> findSequenceUpTo(long number) {
        for (int i = 0; i < numbers.size(); i++) {
            long sum = numbers.get(i);
            for (int j = i + 1; j < numbers.size(); j++) {
                sum += numbers.get(j);
                if (sum == number) {
                    return numbers.subList(i, j + 1);
                } else if (sum > number) {
                    break;
                }
            }
        }
        throw new NoSuchElementException();
    }
}