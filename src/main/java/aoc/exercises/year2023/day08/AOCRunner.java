package aoc.exercises.year2023.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        DesertMap desertMap = inputParser.parseInput();
        solution1 = desertMap.getStepsToEnd();
        solution2 = desertMap.getGhostStepsToEnd();
    }
}