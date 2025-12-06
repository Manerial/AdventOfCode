package aoc.exercises.year2025.day06;

import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Calculation> calculations = inputParser.parseInput();

        solution1 = calculations.stream().mapToLong(Calculation::calculateLine).sum();
        solution2 = calculations.stream().mapToLong(Calculation::calculateColumn).sum();
    }
}