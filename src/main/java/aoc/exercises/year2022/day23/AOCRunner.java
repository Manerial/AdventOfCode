package aoc.exercises.year2022.day23;

import utilities.AbstractAOC;
import utilities.Timer;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/23">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        Timer timer = new Timer();
        InputParser inputParser = new InputParser(inputList);
        Grid grid = inputParser.parseInput();
        int rounds = 0;
        for (; rounds < 10; rounds++) {
            grid.move();
        }
        solution1 = grid.getTotalArea();
        timer.time();
        while (!grid.finalized()) {
            grid.move();
            rounds++;
        }
        solution2 = rounds;
        timer.time();
    }
}