package aoc.exercises.year2021.day07;

import utilities.AbstractAOC;

import java.util.Arrays;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int[] crabsPosition = Arrays.stream(inputList.get(0).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        solution1 = getFuelToAlign(crabsPosition, false);

        solution2 = getFuelToAlign(crabsPosition, true);
    }

    private int getFuelToAlign(int[] crabsPosition, boolean expensiveSolution) {
        int minPosition = Arrays.stream(crabsPosition).min().orElseThrow();
        int maxPosition = Arrays.stream(crabsPosition).max().orElseThrow();
        int minFuel = Integer.MAX_VALUE;
        for (int position = minPosition; position < maxPosition; position++) {
            int currentFuel = getFuelForPosition(crabsPosition, position, expensiveSolution);
            minFuel = Integer.min(minFuel, currentFuel);
        }
        return minFuel;
    }

    private int getFuelForPosition(int[] crabsPosition, int position, boolean expensiveSolution) {
        return Arrays.stream(crabsPosition)
                .map(operand -> {
                    int move = Math.abs(operand - position);
                    if (!expensiveSolution) {
                        return move;
                    } else {
                        return getPascalNumber(move);
                    }
                })
                .sum();
    }

    private int getPascalNumber(int move) {
        int pascalNumber = 0;
        for(int i = 0; i <= move; i++) {
            pascalNumber += i;
        }
        return pascalNumber;
    }
}
