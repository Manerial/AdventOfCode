package aoc.exercises.year2024.day15;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Biguarium {
    private final List<Direction> directions = new ArrayList<>();
    private final Set<Position> biguariumWalls = new HashSet<>();
    private final List<BigObject> biguariumObjects = new ArrayList<>();
    private Position lanternFish;

    public void addLine(String line, int y) {
        if (line.startsWith("#")) {
            addBiguariumLine(line, y);
        } else if (!line.isEmpty()) {
            addDirections(line);
        }
    }

    private void addBiguariumLine(String line, int y) {
        for (int x = 0; x < line.length(); x++) {
            Position position1 = new Position(x * 2, y);
            Position position2 = new Position(x * 2 + 1, y);
            char c = line.charAt(x);
            if (c == '#') {
                biguariumWalls.add(position1);
                biguariumWalls.add(position2);
            } else if (c == 'O') {
                biguariumObjects.add(new BigObject(position1, position2));
            } else if (c == '@') {
                lanternFish = position1;
            }
        }
    }

    public void addDirections(String line) {
        for (char c : line.toCharArray()) {
            directions.add(Direction.charToDirection(c));
        }
    }

    public void moveFish() {
        for (Direction direction : directions) {
            BigObject neighbor = getBigObjectAt(lanternFish.getNeighbor(direction));
            List<BigObject> objectsToMove = new ArrayList<>();
            fillObjectsToMove(neighbor, direction, objectsToMove);
            if (!objectsToMove.isEmpty() && canMove(objectsToMove, direction)) {
                objectsToMove.forEach(bigObject -> bigObject.move(direction));
                lanternFish.move(direction);
            } else if (objectsToMove.isEmpty() && !biguariumWalls.contains(lanternFish.getNeighbor(direction))) {
                lanternFish.move(direction);
            }
        }
    }

    private boolean canMove(List<BigObject> objectsToMove, Direction direction) {
        return objectsToMove.stream()
                .flatMap(bigObject -> Stream.of(bigObject.getPosition1().getNeighbor(direction), bigObject.getPosition2().getNeighbor(direction)))
                .noneMatch(biguariumWalls::contains);
    }

    private BigObject getBigObjectAt(Position neighbor) {
        return biguariumObjects.stream()
                .filter(bigObject -> bigObject.getPosition1().equals(neighbor) || bigObject.getPosition2().equals(neighbor))
                .findAny()
                .orElse(null);
    }

    public void fillObjectsToMove(BigObject currentObject, Direction direction, List<BigObject> objectsToMove) {
        if (currentObject == null) {
            return;
        }
        objectsToMove.add(currentObject);

        List<BigObject> neighbors = getNeighbors(currentObject, direction);
        neighbors.forEach(bigObject -> fillObjectsToMove(bigObject, direction, objectsToMove));
    }

    private List<BigObject> getNeighbors(BigObject currentObject, Direction direction) {
        Position position1 = currentObject.getPosition1().getNeighbor(direction);
        Position position2 = currentObject.getPosition2().getNeighbor(direction);
        return biguariumObjects.stream()
                .filter(bigObject ->
                        bigObject.getPosition1().equals(position1)
                                || bigObject.getPosition2().equals(position1)
                                || bigObject.getPosition1().equals(position2)
                )
                .filter(bigObject -> !bigObject.equals(currentObject))
                .toList();
    }

    public long getScore() {
        return biguariumObjects.stream()
                .map(BigObject::getPosition1)
                .mapToLong(position -> position.getX() + position.getY() * 100L)
                .sum();
    }
}
