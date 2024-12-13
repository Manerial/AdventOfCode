package aoc.exercises.year2024.day13;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/13">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<ClawMachina> clawMachinas = inputParser.parseInput();
        solution1 = clawMachinas.stream()
                .mapToLong(ClawMachina::getTokensForPrize)
                .sum();

        clawMachinas.forEach(clawMachina -> clawMachina.getPrize().increment());

        solution2 = clawMachinas.stream()
                .mapToLong(ClawMachina::getTokensForPrize)
                .sum();
    }
}