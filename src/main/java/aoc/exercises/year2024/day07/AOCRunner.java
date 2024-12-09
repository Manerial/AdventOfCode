package aoc.exercises.year2024.day07;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Equation> equationList = inputParser.parseInput();
        solution1 = equationList.stream()
                .mapToLong(Equation::evaluate)
                .sum();
        solution2 = equationList.stream()
                .mapToLong(Equation::evaluateWithConcat)
                .sum();
    }
}