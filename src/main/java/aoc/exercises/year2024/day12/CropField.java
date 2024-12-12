package aoc.exercises.year2024.day12;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class CropField {
    private final char cropType;
    private final List<Position> field = new ArrayList<>();

    public CropField(Map<Position, Character> field, Position startingCrop) {
        this.cropType = field.get(startingCrop);
        fillRecursively(field, startingCrop);
    }

    public void fillRecursively(Map<Position, Character> fieldMap, Position currentCrop) {
        field.add(currentCrop);

        currentCrop.getDirectNeighbors().stream()
                .filter(fieldMap::containsKey) // neighbor is within the field
                .filter(neighbor -> fieldMap.get(neighbor).equals(cropType))
                .filter(this::notInCrops)
                .forEach(neighbor -> fillRecursively(fieldMap, neighbor));
    }

    public long getPrice() {
        return field.size() * getPerimeter();
    }

    public long getPriceWithDiscount() {
        return field.size() * getSides();
    }

    private long getPerimeter() {
        return getAllEdges().size();
    }

    private long getSides() {
        List<Position> allEdges = new ArrayList<>(getAllEdges());

        long sides = 0;
        while (!allEdges.isEmpty()) { // We must count the inner and outer edges
            Position startingCrop = field.stream()
                    .filter(crop ->
                            crop.getDirectNeighbors().stream().anyMatch(allEdges::contains)
                    ).findAny()
                    .orElseThrow();
            sides += getSides(startingCrop, allEdges);
        }

        return sides;
    }

    private List<Position> getAllEdges() {
        return field.stream()
                .flatMap(crop ->
                        crop.getDirectNeighbors().stream().filter(this::notInCrops)
                )
                .toList();
    }

    private boolean notInCrops(Position neighbor) {
        return !field.contains(neighbor);
    }

    private long getSides(Position startingPosition, List<Position> allEdges) {
        Direction startingDirection = getStartingDirection(startingPosition, allEdges);
        Direction currentDirection = startingDirection;
        Position currentPosition = new Position(startingPosition);
        int sides = 0;

        do {
            currentPosition.move(currentDirection);
            Direction goLeft = currentDirection.turnLeft();
            Direction goFront = currentDirection;
            Direction goRight = currentDirection.turnRight();
            Direction goBack = currentDirection.turnRight().turnRight();

            Position left = currentPosition.getNeighbor(goLeft);
            Position front = currentPosition.getNeighbor(goFront);
            Position right = currentPosition.getNeighbor(goRight);
            Position back = currentPosition.getNeighbor(goBack);

            if (turnOrRemoveEdge(left, allEdges)) {
                sides += 1;
                currentDirection = goLeft;
            } else if (turnOrRemoveEdge(front, allEdges)) {
                // Do nothing if we continue
            } else if (turnOrRemoveEdge(right, allEdges)) {
                sides += 1;
                currentDirection = goRight;
            } else if (turnOrRemoveEdge(back, allEdges)) {
                sides += 2;
                currentDirection = goBack;
            }
        } while (!samePositionAndDirection(startingPosition, currentPosition, currentDirection, startingDirection));
        return sides;
    }

    private Direction getStartingDirection(Position startingCrop, List<Position> allEdges) {
        Direction direction = Direction.NORTH;
        int limit = 4;
        while (!facingGoodDirection(startingCrop, allEdges, direction) && limit > 0) {
            direction = direction.turnRight();
            limit--;
        }
        return direction;
    }

    private boolean facingGoodDirection(Position startingCrop, List<Position> allEdges, Direction direction) {
        Position left = startingCrop.getNeighbor(direction.turnLeft());
        Position front = startingCrop.getNeighbor(direction);
        return allEdges.contains(left) &&
                field.contains(front);
    }

    private boolean turnOrRemoveEdge(Position neighbor, List<Position> allEdges) {
        if (field.contains(neighbor)) {
            return true;
        }
        allEdges.remove(neighbor);
        return false;
    }

    private static boolean samePositionAndDirection(Position startingCrop, Position currentPosition, Direction currentDirection, Direction startingDirection) {
        return currentPosition.equals(startingCrop) && currentDirection == startingDirection;
    }
}
