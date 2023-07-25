package aoc.exercises.year2018.day04;

import utilities.AbstractAOC;
import utilities.errors.NoSuchElementInListException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Guard> guards = inputParser.parseInput();

        Guard biggestSleeper = findBiggestSleeper(guards);
        solution1 = biggestSleeper.getGuardId() * biggestSleeper.getMostSleptMinute();

        Guard guardContainingBiggestOccurrence = findGuardContainingBiggestOccurrence(guards);
        solution2 = getGuardTimesMinuteByOccurrence(guardContainingBiggestOccurrence);
    }

    private Guard findBiggestSleeper(List<Guard> guards) {
        return guards.stream()
                .max(Guard::compareSleep)
                .orElseThrow(NoSuchElementException::new);
    }

    private Guard findGuardContainingBiggestOccurrence(List<Guard> guards) {
        return guards.stream()
                .reduce((guard, guard2) -> (guard.getBiggestOccurrence() > guard2.getBiggestOccurrence()) ? guard : guard2)
                .orElseThrow();
    }

    private int getGuardTimesMinuteByOccurrence(Guard guard) {
        Optional<Integer> minuteByOccurrence = guard.getMinuteByOccurrence(guard.getBiggestOccurrence());
        if (minuteByOccurrence.isPresent()) {
            return guard.getGuardId() * minuteByOccurrence.get();
        }

        throw new NoSuchElementInListException();
    }
}