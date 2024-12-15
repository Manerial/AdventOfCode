package aoc.exercises.year2024.day15;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Aquarium {
    private final List<Direction> directions = new ArrayList<>();
    private final Set<Position> aquariumWalls = new HashSet<>();
    private final List<Position> aquariumObjects = new ArrayList<>();
    private Position lanternFish;

    public void addLine(String line, int y) {
        if (line.startsWith("#")) {
            addAquariumLine(line, y);
        } else if (!line.isEmpty()) {
            addDirections(line);
        }
    }

    private void addAquariumLine(String line, int y) {
        for (int x = 0; x < line.length(); x++) {
            Position position = new Position(x, y);
            char c = line.charAt(x);
            if (c == '#') {
                aquariumWalls.add(position);
            } else if (c == 'O') {
                aquariumObjects.add(position);
            } else if (c == '@') {
                lanternFish = position;
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
            moveObject(lanternFish, direction);
        }
    }

    public boolean moveObject(Position currentObject, Direction direction) {
        Position neighbor = currentObject.getNeighbor(direction);
        if (aquariumWalls.contains(neighbor)) {
            return false;
        } else if (aquariumObjects.contains(neighbor)) {
            Position objectToMove = aquariumObjects.stream()
                    .filter(position -> position.equals(neighbor))
                    .findAny()
                    .orElseThrow();
            boolean canMove = moveObject(objectToMove, direction);
            if (canMove) {
                currentObject.move(direction);
                return true;
            }
        } else {
            currentObject.move(direction);
            return true;
        }
        return false;
    }

    public long getScore() {
        return aquariumObjects.stream()
                .mapToLong(position -> position.getX() + position.getY() * 100L)
                .sum();
    }
}
