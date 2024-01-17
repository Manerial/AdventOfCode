package aoc.exercises.year2021.day08;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<DigitSignal> digitSignals = inputParser.parseInput();
        solution1 = digitSignals.stream().mapToLong(DigitSignal::countEasyOutput).sum();
        solution2 = digitSignals.stream().mapToLong(DigitSignal::getOutput).sum();
    }
}