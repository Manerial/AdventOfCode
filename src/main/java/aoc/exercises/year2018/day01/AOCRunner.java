package aoc.exercises.year2018.day01;

import utilities.AbstractAOC;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> frequencyDeltaList = inputList.stream()
                .map(Integer::parseInt)
                .toList();

        solution1 = frequencyDeltaList.stream()
                .mapToInt(Integer::intValue)
                .sum();

        solution2 = getFirstFrequencyFetchedTwice(frequencyDeltaList);
    }

    private static int getFirstFrequencyFetchedTwice(List<Integer> frequencyDeltaList) {
        int currentFrequency = 0;
        Set<Integer> alreadyFetched = new HashSet<>(Set.of(currentFrequency));
        while (true) {
            for (Integer frequencyDelta : frequencyDeltaList) {
                currentFrequency += frequencyDelta;
                if (!alreadyFetched.add(currentFrequency)) {
                    return currentFrequency;
                }
            }
        }
    }
}