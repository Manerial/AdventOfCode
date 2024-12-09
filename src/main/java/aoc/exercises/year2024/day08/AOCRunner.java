package aoc.exercises.year2024.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        AntennaMap antennaMap = inputParser.parseInput();
        antennaMap.setAllLine(false);
        solution1 = antennaMap.countAntinodesWithinMap();
        antennaMap.setAllLine(true);
        solution2 = antennaMap.countAntinodesWithinMap();
    }
}