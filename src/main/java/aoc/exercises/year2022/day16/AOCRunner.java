package aoc.exercises.year2022.day16;

import utilities.AbstractAOC;


/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/16">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        ValveRoom[] valveRooms = inputParser.parseInput();
        ValveRoom startRoom = valveRooms[0];
        MaxFinder maxFinder = new MaxFinder();
        Explorer[] explorers = new Explorer[1];
        explorers[0] = new Explorer(0, 30, 0);
        maxFinder.findMaxPressure(startRoom, valveRooms, explorers, explorers[0], 0);
        solution1 = maxFinder.getMaxPressure();

        explorers = new Explorer[2];
        explorers[0] = new Explorer(0, 26, 0);
        explorers[1] = new Explorer(1, 26, 0);
        maxFinder.findMaxPressure(startRoom, valveRooms, explorers, explorers[0], 0);
        solution2 = maxFinder.getMaxPressure();
    }
}