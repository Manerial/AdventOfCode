package aoc.exercises.year2019.day03;

import utilities.AbstractAOC;
import aoc.common_objects.Direction;
import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private record Wire(List<Position> positions) {}
    private final List<Wire> wires = new ArrayList<>();
    private final Position start = new Position(0, 0);

    @Override
    public void run() {
        fillWires(inputList);
        List<Position> commonPositions = findCommonPositions();
        Position nearestPosition = findNearestPosition(commonPositions);
        solution1 = nearestPosition.getDistanceFrom0();
        solution2 = findNearestCommonDistance(commonPositions);
    }

    private void fillWires(List<String> input) {
        for (String wireSections : input) {
            Position currentPosition = start;
            List<Position> positions = new ArrayList<>();
            String[] sections = wireSections.split(",");
            for (String section : sections) {
                currentPosition = fillWireSection(currentPosition, positions, section);
            }
            wires.add(new Wire(positions));
        }
    }

    private static Position fillWireSection(Position currentPosition, List<Position> wire, String movement) {
        Position nextPosition = getNextPosition(currentPosition, movement);
        List<Position> interval = Position.interval(currentPosition, nextPosition);
        interval.remove(0); // Already saved
        wire.addAll(interval);
        return nextPosition;
    }

    private static Position getNextPosition(Position currentPosition, String item) {
        Direction direction = Direction.charToDirection(item.charAt(0));
        int steps = Integer.parseInt(item.substring(1));
        Position newPosition = new Position(currentPosition);

        newPosition.move(direction, steps);
        return newPosition;
    }

    private List<Position> findCommonPositions() {
        List<Position> p0 = wires.get(0).positions; // Optimised for a stream/filter
        Set<Position> p1 = new HashSet<>(wires.get(1).positions); // Optimised for a contains
        return p0.stream()
                .filter(p1::contains)
                .toList();
    }

    private Position findNearestPosition(List<Position> commonPositions) {
        return commonPositions.stream()
                .min(Position::minFromOrigin)
                .orElse(start);
    }

    private int findNearestCommonDistance(List<Position> commonPositions) {
        int minPos = Integer.MAX_VALUE;
        for (Position position : commonPositions) {
            int position1 = wires.get(0).positions.indexOf(position) + 1;
            int position2 = wires.get(1).positions.indexOf(position) + 1;
            int minDistance = position1 + position2;
            minPos = Math.min(minDistance, minPos);
        }
        return minPos;
    }
}