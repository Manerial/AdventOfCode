package aoc.exercises.year2023.day10;

import aoc.common_objects.Position;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    char startChar;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<Position, Character> pipesMap = inputParser.parseInput();
        List<Position> pipeLoop = getPipeLoop(pipesMap);
        cleanPipesMap(pipesMap, pipeLoop);
        solution1 = pipeLoop.size() / 2;
        solution2 = pipesMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == '.')
                .filter(entry -> isInside(entry.getKey(), pipesMap))
                .count();
    }

    private void cleanPipesMap(Map<Position, Character> pipesMap, List<Position> pipeLoop) {
        pipesMap.forEach((key, value) -> {
            if (value != '.' && !pipeLoop.contains(key)) {
                pipesMap.put(key, '.');
            }
        });
    }

    private boolean isInside(Position position, Map<Position, Character> pipesMap) {
        int ropeCounter = 0;
        Position northPosition = new Position(position.getX(), 0);
        List<Position> interval = Position.interval(position, northPosition);

        char lastTurn = '.';
        for (Position intervalPosition : interval) {
            Character currentChar = pipesMap.get(intervalPosition);
            if (currentChar == 'S') {
                currentChar = startChar;
            }
            if (currentChar == 'J' || currentChar == 'L') {
                lastTurn = currentChar;
                continue;
            }
            if (currentChar == '-' || currentChar == 'F' && lastTurn == 'J' || currentChar == '7' && lastTurn == 'L') {
                ropeCounter++;
            }
        }
        return ropeCounter % 2 == 1;
    }

    private List<Position> getPipeLoop(Map<Position, Character> pipesMap) {
        boolean finishedLoop = false;
        List<Position> pipeLoop = new ArrayList<>();
        Position currentPosition = pipesMap.entrySet().stream()
                .filter(e -> e.getValue() == 'S')
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
        Position previousPosition = null;
        while (!finishedLoop) {
            pipeLoop.add(currentPosition);
            Pair<Position, Position> options = getOptions(currentPosition, pipesMap);
            if (options.getLeft().equals(previousPosition)) {
                previousPosition = currentPosition;
                currentPosition = options.getRight();
            } else {
                previousPosition = currentPosition;
                currentPosition = options.getLeft();
            }
            finishedLoop = pipesMap.get(currentPosition) == 'S';
        }
        return pipeLoop;
    }

    private Pair<Position, Position> getOptions(Position currentPositon, Map<Position, Character> pipesMap) {
        char currentChar = pipesMap.get(currentPositon);
        Position option1 = new Position(currentPositon);
        Position option2 = new Position(currentPositon);
        if (currentChar == 'S') {
            currentChar = getStartPositionPipe(currentPositon, pipesMap, currentChar);
            startChar = currentChar;
        }
        switch (currentChar) {
            case '|' -> {
                option1.incY();
                option2.decY();
            }
            case '-' -> {
                option1.incX();
                option2.decX();
            }
            case 'F' -> {
                option1.incX();
                option2.incY();
            }
            case 'J' -> {
                option1.decX();
                option2.decY();
            }
            case 'L' -> {
                option1.incX();
                option2.decY();
            }
            case '7' -> {
                option1.decX();
                option2.incY();
            }
            default -> throw new IllegalStateException("Unexpected value: " + currentChar);
        }
        return new ImmutablePair<>(option1, option2);
    }

    private static char getStartPositionPipe(Position currentPositon, Map<Position, Character> pipesMap, char currentChar) {
        Character charNorth = pipesMap.getOrDefault(currentPositon.getNorth(), '.');
        Character charSouth = pipesMap.getOrDefault(currentPositon.getSouth(), '.');
        Character charEast = pipesMap.getOrDefault(currentPositon.getEast(), '.');
        Character charWest = pipesMap.getOrDefault(currentPositon.getWest(), '.');

        boolean canGoNorth = charNorth == 'F' || charNorth == '|' || charNorth == '7';
        boolean canGoSouth = charSouth == 'L' || charSouth == '|' || charSouth == 'J';
        boolean canGoEast = charEast == '7' || charEast == '-' || charEast == 'J';
        boolean canGoWest = charWest == 'F' || charWest == '-' || charWest == 'L';

        if (canGoNorth && canGoSouth) {
            currentChar = '|';
        } else if (canGoEast && canGoWest) {
            currentChar = '-';
        } else if (canGoNorth && canGoEast) {
            currentChar = 'L';
        } else if (canGoSouth && canGoWest) {
            currentChar = '7';
        } else if (canGoNorth && canGoWest) {
            currentChar = 'J';
        } else if (canGoSouth && canGoEast) {
            currentChar = 'F';
        }
        return currentChar;
    }
}