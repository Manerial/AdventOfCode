package aoc.exercises.year2019.day01;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> masses = inputList.stream()
                .map(Integer::parseInt)
                .toList();
        solution1 = sumList(getFuel(masses));

        solution2 = operateRecursive(masses);
    }

    private int operateRecursive(List<Integer> masses) {
        List<Integer> fuelNeeded = getFuel(masses);
        int sum = sumList(fuelNeeded);
        if (sum != 0) {
            sum += operateRecursive(fuelNeeded);
        }
        return sum;
    }

    private int sumList(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Integer> getFuel(List<Integer> masses) {
        return masses.stream()
                .map(integer -> {
                    int sum = integer / 3 - 2;
                    return Math.max(sum, 0);
                })
                .toList();
    }
}