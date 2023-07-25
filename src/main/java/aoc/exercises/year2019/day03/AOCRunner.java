package aoc.exercises.year2019.day03;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private record Wire(List<Position> positions) {
    }

    private final List<Wire> wires = new ArrayList<>();
    private final Position start = new Position(0, 0);

    @Override
    public void run() {
        fillWires(inputList);
        List<Position> intersections = findIntersections();
        Position nearestIntersection = findNearestIntersection(intersections);
        solution1 = nearestIntersection.getDistanceFrom0();
        solution2 = findNearestCommonDistance(intersections);
    }

    /**
     * Fills the wires by processing the input list of wire definitions.
     * Each wire definition contains sections separated by commas that determine
     * the movement and positions of the wire.
     *
     * @param input a list of strings where each string represents the definition
     *              of a wire, containing movement instructions separated by commas
     */
    private void fillWires(List<String> input) {
        for (String wireSections : input) {
            Position currentPosition = start;
            Wire wire = new Wire(new ArrayList<>());
            String[] sections = wireSections.split(",");
            for (String section : sections) {
                currentPosition = fillWireSection(currentPosition, wire, section);
            }
            wires.add(wire);
        }
    }

    /**
     * Fills a section of the wire based on the movement instruction,
     * adding all intermediate positions between the current position and the next position.
     *
     * @param currentPosition the current position of the wire before applying the movement
     * @param wire            the wire whose positions are being filled
     * @param movement        the movement instruction specifying direction and steps
     * @return the next position of the wire after the movement
     */
    private static Position fillWireSection(Position currentPosition, Wire wire, String movement) {
        Position nextPosition = getNextPosition(currentPosition, movement);
        List<Position> interval = Position.interval(currentPosition, nextPosition);
        interval.removeFirst(); // Already saved
        wire.positions.addAll(interval);
        return nextPosition;
    }

    /**
     * Calculates and returns the next position based on the current position and the given movement instruction.
     *
     * @param currentPosition the current position to calculate the next position from
     * @param item            the string representation of the movement instruction (direction and steps)
     * @return the new position after applying the movement instruction
     */
    private static Position getNextPosition(Position currentPosition, String item) {
        Direction direction = Direction.charToDirection(item.charAt(0));
        int steps = Integer.parseInt(item.substring(1));
        Position newPosition = new Position(currentPosition);
        newPosition.move(direction, steps);
        return newPosition;
    }

    /**
     * Identifies and returns a list of positions where the paths of multiple wires intersect.
     * The intersections are determined by finding common positions shared among all wires.
     *
     * @return a list of positions representing intersections between all wires.
     * Returns an empty list if no intersections are found.
     */
    private List<Position> findIntersections() {
        List<Position> intersections = new ArrayList<>(wires.getFirst().positions);
        wires.stream()
                .skip(1)
                .map(wire -> new HashSet<>(wire.positions)) // HashSet is more optimized for a retainAll
                .forEach(intersections::retainAll);
        return intersections;
    }

    /**
     * Finds the nearest intersection to the origin from a list of given positions.
     * If the list is empty, the starting position is returned.
     *
     * @param intersections a list of positions representing intersections.
     * @return the nearest position to the origin from the given list of intersections, or the starting position if the list is empty.
     */
    private Position findNearestIntersection(List<Position> intersections) {
        return intersections.stream()
                .min(Position::minFromOrigin)
                .orElse(start);
    }

    /**
     * Finds and returns the shortest combined distance from the start of all wires
     * to any of the given intersection points. The combined distance is calculated
     * as the sum of distances each wire travels to the intersection.
     *
     * @param intersections a list of positions representing intersections between wires
     * @return the shortest combined distance to an intersection point, or Integer.MAX_VALUE
     * if the list of intersections is empty
     */
    private int findNearestCommonDistance(List<Position> intersections) {
        int minPos = Integer.MAX_VALUE;
        for (Position position : intersections) {
            int minDistance = wires.stream()
                    .mapToInt(wire -> wire.positions.indexOf(position) + 1)
                    .sum();
            minPos = Math.min(minDistance, minPos);
        }
        return minPos;
    }
}