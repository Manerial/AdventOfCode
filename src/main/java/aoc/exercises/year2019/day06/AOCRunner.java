package aoc.exercises.year2019.day06;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList, "COM");
        Planet firstPlanet = inputParser.parseInput();
        solution1 = firstPlanet.getTotalOrbits(0);
        solution2 = firstPlanet.getDistanceBetween("YOU", "SAN");
    }
}
