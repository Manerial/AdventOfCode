package aoc.exercises.year2020.day10;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private List<Integer> pathCombinations;

    @Override
    public void run() {
        List<Integer> joltages = getJoltages();
        initCombinations();

        solution1 = getResult(joltages);
        solution2 = getTotalCombinations(joltages);
    }


    /**
     * Combine the last 3 digits of an operation until index 20.
     * This represents all the different ways to travel a graph of N consecutive nodes with 3 options each.
     * Example :
     * 0 -> 1 (arbitrary)
     * 1 -> 1 (only 1 way to go through a path of length 1)
     * 2 -> 2 (1+1)
     * 3 -> 4 (1+1+2)
     * 4 -> 7 (2+3+4)
     * 5 -> 13 (3+4+7)
     * And so on.
     */
    private void initCombinations() {
        pathCombinations = new ArrayList<>(List.of(0, 0, 1));
        for (int i = 0; i < 20; i++) {
            int sumLast3 = pathCombinations.stream()
                    .skip(pathCombinations.size() - 3L)
                    .mapToInt(Integer::intValue)
                    .sum();
            pathCombinations.add(sumLast3);
        }
        pathCombinations.remove(0);
        pathCombinations.remove(1);
    }

    /**
     * Sort the adapters in ascending order and add the charging outler (0) and the final adapter (max + 3).
     *
     * @return the list of sorted adapters
     */
    private List<Integer> getJoltages() {
        List<Integer> adapters = new ArrayList<>(inputList.stream().map(Integer::parseInt)
                .sorted()
                .toList());
        adapters.add(0, 0);
        adapters.add(adapters.size(), adapters.get(adapters.size() - 1) + 3);
        return adapters;
    }

    /**
     * get the result of the total differences of 1 multiplied by the total differences of 3.
     *
     * @param joltages : the list of joltages steps
     * @return the result computed
     */
    private static int getResult(List<Integer> joltages) {
        int diff1 = 0;
        int diff3 = 0;
        for (int i = 0; i < joltages.size() - 1; i++) {
            int diff = joltages.get(i + 1) - joltages.get(i);
            if (diff == 1) {
                diff1++;
            } else if (diff == 3) {
                diff3++;
            }
        }
        return diff1 * diff3;
    }

    /**
     * For each joltage, get the possible amount of paths, then multiply them together to get the total amount of paths combinations.
     *
     * @param joltages : the list of joltages steps
     * @return the amount of different ways to connect all the adapters
     */
    private long getTotalCombinations(List<Integer> joltages) {
        long totalCombinations = 1;

        // Keep tracking of the serie of adapters that can be plugged in one row
        int countSerie = 1;
        for (int i = 0; i < joltages.size() - 1; i++) {
            int possibleAdapters = countPossibleAdapters(i, joltages);
            // If we have more than 1 adapter that can be plugged, increment our possible paths by one
            if (possibleAdapters > 1) {
                countSerie++;
            }
            // If exactly two adapters can be plugged at this step, stop counting and get the paths combinations
            if (possibleAdapters == 2) {
                totalCombinations *= pathCombinations.get(countSerie);
                countSerie = 1;
            }
        }
        return totalCombinations;
    }

    /**
     * Get the potential adapters that can be plug on the current one
     *
     * @param index    : the index in the list of joltages
     * @param joltages : the list of joltages steps
     * @return the number of possible adapters
     */
    private int countPossibleAdapters(int index, List<Integer> joltages) {
        int joltage = joltages.get(index);
        int fromIndex = index + 1;
        int toIndex = Math.min(fromIndex + 3, joltages.size());
        List<Integer> next3Adapters = joltages.subList(fromIndex, toIndex);

        return next3Adapters.stream()
                .filter(joltageToTest -> joltage + 3 >= joltageToTest)
                .toList()
                .size();
    }
}