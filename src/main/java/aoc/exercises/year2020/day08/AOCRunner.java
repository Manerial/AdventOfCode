package aoc.exercises.year2020.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Program program = inputParser.parseInput();
        program.executeUntilLoop();
        solution1 = program.getAccumulator();
        program.debug();
        solution2 = program.getAccumulator();
    }
}