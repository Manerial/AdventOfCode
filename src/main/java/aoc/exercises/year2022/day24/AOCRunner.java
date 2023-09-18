package aoc.exercises.year2022.day24;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/24">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Grid grid;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        grid = inputParser.parseInput();
        int time = reachEnd();
        solution1 = time;
        grid.cleanAndReverseGoal();
        time += reachEnd();
        grid.cleanAndReverseGoal();
        time += reachEnd();
        solution2 = time;
    }

    private int reachEnd() {
        int time = 0;
        while (!grid.isFinished()) {
            grid.blowOnMap();
            grid.populate();
            time++;
        }
        return time;
    }
}