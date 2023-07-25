package aoc.exercises.year2017.day03;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        Integer input = Integer.parseInt(inputList.get(0));
        Position inputPosition = computeInputPosition(input);
        solution1 = inputPosition.getManhattanDistance(new Position(0, 0));
        solution2 = getFirstValueGreaterThan(input);
    }

    private Position computeInputPosition(Integer input) {
        Position currentPosition = new Position(0, 0);
        Direction direction = Direction.EAST;

        for (int i = 1; i < input; i++) {
            currentPosition.move(direction);
            direction = getNextDirection(direction, currentPosition);
        }
        return currentPosition;
    }

    private int getFirstValueGreaterThan(Integer input) {
        Position currentPosition = new Position(0, 0);
        Direction direction = Direction.EAST;
        Map<Position, Integer> grid = new HashMap<>();
        int sumNeighborValues = 0;

        while (sumNeighborValues <= input) {
            sumNeighborValues = sumNeighborValues(grid, currentPosition);
            grid.put(new Position(currentPosition), sumNeighborValues);
            currentPosition.move(direction);
            direction = getNextDirection(direction, currentPosition);
        }

        return sumNeighborValues;
    }

    /**
     * Depending on our current position and direction, choose our next direction
     * <pre>
     * v<<<<
     * vv<<^
     * vv>^^
     * v>>>^
     * >>>>?
     * </pre>
     *
     * @param direction : the current direction we are facing
     * @param position  : the current position
     * @return the next direction to go
     */
    private Direction getNextDirection(Direction direction, Position position) {
        Direction nextCardinal = direction;
        if (isRightBottomCorner(position)) {
            nextCardinal = Direction.EAST;
        } else if (isNextRightBottomCorner(position)) {
            nextCardinal = Direction.NORTH;
        } else if (isInCorner(position)) {
            nextCardinal = direction.turnLeft();
        }

        return nextCardinal;
    }

    /**
     * Check if the position is in the right-bottom corner
     *
     * <pre>
     * v<<
     * v>^
     * >>?
     * </pre>
     *
     * @param position : the position to check
     * @return true if the position is in the right bottom corner
     */
    private boolean isRightBottomCorner(Position position) {
        int x = position.getX();
        int y = position.getY();
        return y >= 0 && x == y;
    }

    /**
     * Check if the position is next to the right-bottom corner
     *
     * <pre>
     * v<<
     * v>^
     * >>>?
     * </pre>
     *
     * @param position : the position to check
     * @return true if the position is in the next position of the right bottom corner
     */
    private boolean isNextRightBottomCorner(Position position) {
        int x = position.getX();
        int y = position.getY();
        return y >= 0 && x - 1 == y;
    }


    /**
     * Check if the position is in any corner
     *
     * <pre>
     * ?-?
     * |0|
     * ?-?
     * </pre>
     *
     * @param position : the position to check
     * @return true if the position is in any corner
     */
    private boolean isInCorner(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x == y || x == -y;
    }

    /**
     * We start our neighborPosition at currentPosition P, then we check positions 1 to 8
     * <pre>
     *   432
     *   5P1
     *   678
     * If a position is not in the grid, it's value is 0 by default
     * </pre>
     *
     * @param grid            the current grid where we can get the neighbor values
     * @param currentPosition the current position in the grid
     * @return the sum of the neighbors of a position
     */
    private int sumNeighborValues(Map<Position, Integer> grid, Position currentPosition) {
        int sumNeighborValues = 0;

        Position neighborPosition = new Position(currentPosition);
        neighborPosition.incX();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 1
        neighborPosition.incY();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 2
        neighborPosition.decX();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 3
        neighborPosition.decX();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 4
        neighborPosition.decY();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 5
        neighborPosition.decY();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 6
        neighborPosition.incX();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 7
        neighborPosition.incX();
        sumNeighborValues += grid.getOrDefault(neighborPosition, 0); // 8

        return sumNeighborValues > 0 ? sumNeighborValues : 1;
    }
}