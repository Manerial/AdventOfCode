package aoc.exercises.year2024.day04;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<Position, Character> map = inputParser.parseInput();
        solution1 = countXMAS(map);
        solution2 = countX_MAS(map);
    }

    /**
     * Count the number of "XMAS" in the given map, in any direction (diagonal included)
     *
     * @param map map containing the input data
     * @return the number of "XMAS" in the given map
     */
    private int countXMAS(Map<Position, Character> map) {
        return map.keySet().stream()
                .mapToInt(position -> countXMASFromPosition(map, position))
                .sum();
    }

    /**
     * <pre>
     * Count the number of double MAS crossed-shaped (like a X, not a +).
     * There are two examples:
     *   M S
     *    A
     *   M S
     * </pre>
     *
     * @param map map containing the input data
     * @return the number of double MAS crossed-shaped in the given map
     */
    private long countX_MAS(Map<Position, Character> map) {
        return map.keySet().stream()
                .filter(position -> isX_MAS(map, position))
                .count();
    }

    private int countXMASFromPosition(Map<Position, Character> map, Position xPosition) {
        if (map.get(xPosition) != 'X') {
            return 0;
        }
        int count = 0;
        for (Position neighbor : xPosition.getAllNeighbors()) {
            int endX = xPosition.getX() + (neighbor.getX() - xPosition.getX()) * 3;
            int endY = xPosition.getY() + (neighbor.getY() - xPosition.getY()) * 3;
            Position endPosition = new Position(endX, endY);
            String string = getStringFromMapInterval(map, xPosition, endPosition);
            count += string.equals("XMAS") ? 1 : 0;
        }
        return count;
    }

    private boolean isX_MAS(Map<Position, Character> map, Position aPosition) {
        if (map.get(aPosition) != 'A') {
            return false;
        }
        int count = 0;
        for (Position neighbor : aPosition.getDiagNeighbors()) {
            if (map.getOrDefault(neighbor, '.') == 'M') {
                int endX = aPosition.getX() - (neighbor.getX() - aPosition.getX());
                int endY = aPosition.getY() - (neighbor.getY() - aPosition.getY());
                Position endPosition = new Position(endX, endY);
                String string = getStringFromMapInterval(map, neighbor, endPosition);
                count += string.equals("MAS") ? 1 : 0;
            }
        }
        return count == 2;
    }

    private static String getStringFromMapInterval(Map<Position, Character> map, Position start, Position end) {
        List<Position> interval = Position.interval(start, end);
        StringBuilder sb = new StringBuilder();
        for (Position position : interval) {
            sb.append(map.getOrDefault(position, '.'));
        }
        return sb.toString();
    }
}