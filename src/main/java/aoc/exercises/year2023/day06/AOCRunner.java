package aoc.exercises.year2023.day06;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        InputParser.Result result = inputParser.parseInput();
        solution1 = result.races().stream()
                .mapToLong(Race::getWaysToWin)
                .reduce((left, right) -> left * right)
                .orElse(0);
        solution2 = result.race().getWaysToWin();
    }
}