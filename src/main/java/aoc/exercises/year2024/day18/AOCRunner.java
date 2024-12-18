package aoc.exercises.year2024.day18;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/18">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int mapSize = isExample ? 7 : 71;
        int limit = isExample ? 12 : 1024;
        InputParser inputParser = new InputParser(inputList);
        List<Position> corruptedPositions = inputParser.parseInput();
        MemoryMaze memoryMaze = new MemoryMaze();
        memoryMaze.init(mapSize, corruptedPositions.stream().limit(limit).toList());

        solution1 = memoryMaze.reachEnd();

        for (Position corruptedPosition : corruptedPositions.stream().skip(limit).toList()) {
            memoryMaze.corrupt(corruptedPosition);
            memoryMaze.removeAlonePaths();

            if (!memoryMaze.canReachEnd()) {
                solution2 = corruptedPosition.getX() + "," + corruptedPosition.getY();
                break;
            }
        }
    }
}