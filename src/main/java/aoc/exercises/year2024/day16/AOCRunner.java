package aoc.exercises.year2024.day16;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/16">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Maze maze = inputParser.parseInput();
        maze.computePathScore();
        solution1 = maze.getPathToEndValue();
        solution2 = maze.countTilesOnCriticalPath();
    }
}