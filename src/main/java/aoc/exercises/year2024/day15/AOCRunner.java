package aoc.exercises.year2024.day15;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/15">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Aquarium aquarium = inputParser.parseInput();
        aquarium.moveFish();
        solution1 = aquarium.getScore();
        Biguarium biguarium = inputParser.parseSndInput();
        biguarium.moveFish();
        solution2 = biguarium.getScore();
    }
}