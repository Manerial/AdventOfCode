package aoc.exercises.year2018.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Node root = inputParser.parseInput();
        solution1 = root.getSumMetadata();
        solution2 = root.getValue();
    }

}
