package aoc.exercises.year2020.day06;

import utilities.AbstractAOC;

import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Set<Character>> anyYes = inputParser.parseInput();
        solution1 = anyYes.stream().mapToInt(Set::size).sum();

        inputParser.switchParser();
        List<Set<Character>> everyYes = inputParser.parseInput();
        solution2 = everyYes.stream().mapToInt(Set::size).sum();
    }
}
