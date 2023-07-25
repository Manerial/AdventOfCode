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
    private Position alreadyVisited = null;

    @Override
    public void run() {
        List<String> item = Arrays.stream(inputList.get(0).split(", ")).toList();
        Position startPosition = new Position(0, 0);
        Position finalPosition = getFinalPosition(startPosition, item);
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
    private Position getFinalPosition(Position startPosition, List<String> instructions) {
        Direction direction = Direction.NORTH;
        List<Position> positions = new ArrayList<>();
        positions.add(startPosition);

        for (String step : instructions) {
            String turn = step.substring(0, 1);
            int distance = Integer.parseInt(step.substring(1));
            direction = direction.turnLR(turn);
            saveNextPositions(positions, direction, distance);
        }

        return positions.get(positions.size() - 1);
    }

    /**
     * Saves the next positions in the list.
     *
     * @param positions : The list of positions we have visited
     * @param direction : The direction we are following
     * @param distance  : The distance we have to follow
     */
    private void saveNextPositions(List<Position> positions, Direction direction, int distance) {
        Position currentPosition = positions.get(positions.size() - 1);
        int x = currentPosition.getX() + getXDistance(direction, distance);
        int y = currentPosition.getY() + getYDistance(direction, distance);
        Position nextPosition = new Position(x, y);
        List<Position> interval = Position.interval(currentPosition, nextPosition);
        interval
                .subList(1, interval.size()) // Exclude the first position to not visit it twice
                .forEach(position -> {
                    // Save the first position visited twice
                    if (positions.contains(position) && alreadyVisited == null) {
                        alreadyVisited = position;
                    }
                    positions.add(position);
                });
    }

    /**
     * Return the corrected distance if direction is EAST or WEST
     *
     * @param direction : The direction we are following
     * @param distance  : The distance we have to follow
     * @return The corrected distance
     */
    private static int getXDistance(Direction direction, int distance) {
        switch (direction) {
            case EAST -> {
                return distance;
            }
            case WEST -> {
                return -distance;
            }
            default -> {
                return 0;
            }
        }
    }


    /**
     * Return the corrected distance if direction is NORTH or SOUTH
     *
     * @param direction : The direction we are following
     * @param distance  : The distance we have to follow
     * @return The corrected distance
     */
    private static int getYDistance(Direction direction, int distance) {
        switch (direction) {
            case NORTH -> {
                return distance;
            }
            case SOUTH -> {
                return -distance;
            }
            default -> {
                return 0;
            }
        }
    }
}