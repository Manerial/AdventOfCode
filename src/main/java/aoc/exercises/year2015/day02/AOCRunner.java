package aoc.exercises.year2015.day02;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Present> presents = inputParser.parseInput();

        solution1 = presents.stream().mapToInt(Present::getPaper).sum();
        solution2 = presents.stream().mapToInt(Present::getRibbon).sum();
    }
}