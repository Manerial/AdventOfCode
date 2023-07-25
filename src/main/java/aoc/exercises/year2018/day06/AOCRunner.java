package aoc.exercises.year2018.day06;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final Map<Position, Position> gridOfNearestPositions = new HashMap<>();
    private List<Position> inputPositions;
    private int minLimit;
    private int maxLimit;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        PositionsMinMax recordPosMinMax = inputParser.parseInput();

        inputPositions = recordPosMinMax.positions();
        minLimit = recordPosMinMax.min();
        maxLimit = recordPosMinMax.max();

        fillGridWithInputPositions();
        buildGridOfNearestPositions();

        solution1 = countCellsOfBiggestInnerZone();

        int range = isExample ? 32 : 10000;
        solution2 = countPositionsInRegion(range);
    }

    private void fillGridWithInputPositions() {
        inputPositions.forEach(inputPosition -> gridOfNearestPositions.put(inputPosition, inputPosition));
    }

    private void buildGridOfNearestPositions() {
        for (int x = minLimit; x <= maxLimit; x++) {
            for (int y = minLimit; y <= maxLimit; y++) {
                Position position = new Position(x, y);
                Position nearestInputPosition = getNearestInputPosition(position);
                gridOfNearestPositions.put(position, nearestInputPosition);
            }
        }
    }

    private Position getNearestInputPosition(Position position) {
        Map<Position, Integer> manathanDistances = inputPositions.stream()
                .collect(Collectors.toMap(Function.identity(), position1 -> position1.getManhattanDistance(position)));

        Map.Entry<Position, Integer> minEntry = manathanDistances.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow();

        if (Collections.frequency(manathanDistances.values(), minEntry.getValue()) == 1) {
            return minEntry.getKey();
        } else {
            return null;
        }
    }

    private int countCellsOfBiggestInnerZone() {
        List<Position> innerPositions = getInnerPositions();
        Map<Position, Long> countOccurencesOfInnerPosition = gridOfNearestPositions.values().stream()
                .filter(innerPositions::contains)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countOccurencesOfInnerPosition.values().stream()
                .mapToInt(Long::intValue)
                .max()
                .orElseThrow();
    }

    /**
     * Remove the positions that touch the borders of the grid.
     * Those are considered infinite zones.
     *
     * @return the list of positions that are in limited zones.
     */
    private List<Position> getInnerPositions() {
        List<Position> innerPositions = new ArrayList<>(inputPositions);

        for (int i = minLimit; i < maxLimit; i++) {
            Position position1 = gridOfNearestPositions.get(new Position(i, minLimit));
            Position position2 = gridOfNearestPositions.get(new Position(minLimit, i));
            Position position3 = gridOfNearestPositions.get(new Position(i, maxLimit));
            Position position4 = gridOfNearestPositions.get(new Position(maxLimit, i));
            innerPositions.remove(position1);
            innerPositions.remove(position2);
            innerPositions.remove(position3);
            innerPositions.remove(position4);
        }
        return innerPositions;
    }

    private int countPositionsInRegion(int range) {
        int count = 0;

        for (int i = minLimit; i <= maxLimit; i++) {
            for (int j = minLimit; j <= maxLimit; j++) {
                Position currentPosition = new Position(i, j);
                int maxDistance = inputPositions.stream()
                        .mapToInt(position -> position.getManhattanDistance(currentPosition))
                        .sum();
                if (maxDistance < range) {
                    count++;
                }
            }
        }

        return count;
    }
}
