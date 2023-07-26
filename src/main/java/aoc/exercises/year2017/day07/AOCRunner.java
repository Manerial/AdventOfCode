package aoc.exercises.year2017.day07;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Program program = inputParser.parseInput();

        solution1 = program.getName();
        WrongWeightProgram wrongWeightProgram = program.findWrongWeightProgram(0);

        solution2 = wrongWeightProgram.shallBe();
    }

}
