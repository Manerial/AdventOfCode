package aoc.exercises.year2024.day06;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<Position, Character> map = inputParser.parseInput();
        Position currentPosition = inputParser.getCurrentPosition();
        Direction currentDirection = inputParser.getCurrentDirection();

        Map<Position, Character> firstVisitMap = getCloneMap(map);
        visitMap(firstVisitMap, new Position(currentPosition), currentDirection);
        List<Position> visitedPositions = firstVisitMap.entrySet().stream()
                .filter(entry -> entry.getValue() == 'X')
                .map(Map.Entry::getKey)
                .toList();
        solution1 = visitedPositions.size();

        List<Position> potentialObstacles = new ArrayList<>(visitedPositions);
        potentialObstacles.remove(currentPosition);

        solution2 = potentialObstacles.stream()
                .filter(potentialObstacle -> {
                    map.put(potentialObstacle, '#');
                    boolean isLoop = isLoop(map, new Position(currentPosition), currentDirection);
                    map.put(potentialObstacle, '.');
                    return isLoop;
                })
                .count();
    }

    private static Map<Position, Character> getCloneMap(Map<Position, Character> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void visitMap(Map<Position, Character> map, Position currentPosition, Direction currentDirection) {
        while (true) {
            Position nextPosition = new Position(currentPosition);
            nextPosition.move(currentDirection);
            char nextChar = map.getOrDefault(nextPosition, 'O');

            switch (nextChar) {
                case 'O' -> {
                    map.put(new Position(currentPosition), 'X');
                    return;
                }
                case '#' -> currentDirection = currentDirection.turnRight();
                default -> {
                    map.put(currentPosition, 'X');
                    currentPosition = nextPosition;
                }
            }
        }
    }

    private boolean isLoop(Map<Position, Character> map, Position currentPosition, Direction currentDirection) {
        List<Pair<Position, Direction>> visitedPosAndDir = new ArrayList<>();
        while (true) {
            Position nextPosition = new Position(currentPosition);
            nextPosition.move(currentDirection);
            char nextChar = map.getOrDefault(nextPosition, 'O');

            switch (nextChar) {
                case 'O' -> {
                    return false;
                }
                case '#' -> {
                    currentDirection = currentDirection.turnRight();
                    Pair<Position, Direction> posAndDir = Pair.of(new Position(currentPosition), currentDirection);
                    if (visitedPosAndDir.contains(posAndDir)) {
                        return true;
                    }
                    visitedPosAndDir.add(posAndDir);
                }
                default -> currentPosition = nextPosition;
            }
        }
    }
}