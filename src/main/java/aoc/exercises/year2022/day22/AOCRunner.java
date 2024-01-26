package aoc.exercises.year2022.day22;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/22">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Grid grid = inputParser.parseInput();
        grid.resetPosition();
        grid.moveThrough();
        solution1 = grid.getFinalPassword();
        grid.resetPosition();
        grid.useCube(true);
        grid.moveThrough();
        solution2 = grid.getFinalPassword();
    }
}