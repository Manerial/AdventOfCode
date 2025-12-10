package aoc.exercises.year2025.day10;

import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Machine> machines = inputParser.parseInput();
        solution1 = machines.stream().mapToInt(Machine::getMinLightButtonPress).sum();
        solution2 = machines.stream().mapToInt(Machine::getMinJoltageButtonPress).sum();
    }
}