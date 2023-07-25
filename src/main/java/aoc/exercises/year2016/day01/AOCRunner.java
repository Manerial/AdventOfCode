package aoc.exercises.year2016.day01;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    List<Position> positions = new ArrayList<>();
    private Position alreadyVisited = null;

    @Override
    public void run() {
        List<String> item = Arrays.stream(inputList.get(0).split(", ")).toList();
        Position startPosition = new Position(0, 0);
        Position finalPosition = computeInstructions(startPosition, item);
        solution1 = finalPosition.getManhattanDistance(startPosition);

        assert alreadyVisited != null;
        solution2 = alreadyVisited.getManhattanDistance(startPosition);
    }

    /**
     * Follows the input instructions and returns the final position the program reached.
     * It will also store the first position visited twice.
     *
     * @param startPosition : the starting position
     * @param instructions  : the input instructions
     * @return The final position
     */
    private Position computeInstructions(Position startPosition, List<String> instructions) {
        Position currentPosition = new Position(startPosition);
        positions.add(startPosition);
        Direction currentDirection = Direction.NORTH;

        for (String step : instructions) {
            int distance = Integer.parseInt(step.substring(1));
            currentDirection = currentDirection.turnLR(step.substring(0, 1));

            Position oldPosition = new Position(currentPosition);
            currentPosition.move(currentDirection, distance);
            saveNextPositions(currentPosition, oldPosition);
        }

        return currentPosition;
    }

    /**
     * Saves the next positions in the list.
     *
     * @param currentPosition : The current position we are on
     * @param oldPosition : The old position we moved from
     */
    private void saveNextPositions(Position currentPosition, Position oldPosition) {
        if(alreadyVisited != null) {
            return;
        }
        List<Position> interval = Position.interval(oldPosition, currentPosition);
        for(int i = 1; i < interval.size(); i++) { // Exclude the first position of the interval to not visit it twice
            Position position = interval.get(i);
            if (positions.contains(position)) {
                // Exercise 2 : Save the first position visited twice
                alreadyVisited = position;
                return;
            }
            positions.add(position);
        }
    }
}