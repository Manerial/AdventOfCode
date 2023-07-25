package aoc.exercises.year2015.day06;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<FromToSwitch> fromToSwitches = inputParser.parseInput();
        int size = 1000;
        int[][] lightGrid = new int[size][size];
        int[][] powerLightGrid = new int[size][size];

        fromToSwitches.forEach(fromToSwitch -> perform(fromToSwitch, lightGrid, powerLightGrid));

        solution1 = getTotalPower(lightGrid);
        solution2 = getTotalPower(powerLightGrid);
    }

    /**
     * Perform a switch on a rectangle of fromToSwitch.from to fromToSwitch.to
     *
     * @param fromToSwitch   : a rectangle of fromToSwitch.from to fromToSwitch.to and the switch to operate
     * @param lightGrid      : a 2D array of integers representing the light grid
     * @param powerLightGrid : a 2D array of integers representing the power light grid
     */
    private static void perform(FromToSwitch fromToSwitch, int[][] lightGrid, int[][] powerLightGrid) {
        Position from = fromToSwitch.from();
        Position to = fromToSwitch.to();

        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                fromToSwitch.switchLights(lightGrid, x, y);
                fromToSwitch.powerSwitchLights(powerLightGrid, x, y);
            }
        }
    }

    /**
     * Get the sum of each light power of the grid.
     *
     * @param grid : a 2D array of integers representing the light grid
     * @return the sum of each light power of the grid
     */
    private static int getTotalPower(int[][] grid) {
        return Arrays.stream(grid)
                .flatMapToInt(Arrays::stream)
                .sum();
    }
}
