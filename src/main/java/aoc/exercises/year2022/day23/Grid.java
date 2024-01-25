package aoc.exercises.year2022.day23;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;

import java.util.*;

public class Grid {
    private final List<Elve> elves = new ArrayList<>();
    private final Set<Position> positions = new HashSet<>();

    protected static final List<Direction> directions = new ArrayList<>(List.of(
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    ));

    public void addElve(Position position, Elve elve) {
        positions.add(position);
        elves.add(elve);
    }

    private void roundDirections() {
        Direction move = directions.remove(0);
        directions.add(move);
    }

    public void move() {
        elves.forEach(elve -> elve.setAllowedMoves(getAllowedMoves(elve)));
        elves.stream()
                .filter(Elve::canMove)
                .forEach(this::targetPosition);
        elves.stream()
                .filter(Elve::canMove)
                .forEach(elve -> {
                    positions.remove(elve.getPosition());
                    elve.moveIfPossible(elves);
                    positions.add(elve.getPosition());
                });
        elves.forEach(Elve::clearTarget);

        roundDirections();
    }

    private void targetPosition(Elve elve) {
        Position target = new Position(elve.getPosition());
        switch (getPossibleDirection(elve)) {
            case NORTH -> target.decY();
            case SOUTH -> target.incY();
            case WEST -> target.decX();
            case EAST -> target.incX();
        }
        elve.setTarget(target);
    }

    private Direction getPossibleDirection(Elve elve) {
        for (Direction direction : directions) {
            if (!elve.canMoveTo(direction)) {
                continue;
            }
            return direction;
        }
        throw new NoSuchElementException("Elve should move, but can't");
    }

    public boolean finalized() {
        return elves.stream()
                .noneMatch(Elve::canMove);
    }

    public List<Direction> getAllowedMoves(Elve elve) {
        List<Direction> allowedDirections = new ArrayList<>();
        if (hasNoNeighborIn(elve, Direction.NORTH)) {
            allowedDirections.add(Direction.NORTH);
        }
        if (hasNoNeighborIn(elve, Direction.SOUTH)) {
            allowedDirections.add(Direction.SOUTH);
        }
        if (hasNoNeighborIn(elve, Direction.WEST)) {
            allowedDirections.add(Direction.WEST);
        }
        if (hasNoNeighborIn(elve, Direction.EAST)) {
            allowedDirections.add(Direction.EAST);
        }
        return allowedDirections;
    }

    public boolean hasNoNeighborIn(Elve elve, Direction direction) {
        Position position = elve.getPosition();
        List<Position> neighbors = null;
        switch (direction) {
            case NORTH -> neighbors = List.of(
                    new Position(position.getX(), position.getY() - 1), // N
                    new Position(position.getX() + 1, position.getY() - 1), // NE
                    new Position(position.getX() - 1, position.getY() - 1) // NW
            );
            case SOUTH -> neighbors = List.of(
                    new Position(position.getX(), position.getY() + 1), // S
                    new Position(position.getX() + 1, position.getY() + 1), // SE
                    new Position(position.getX() - 1, position.getY() + 1) // SW
            );
            case EAST -> neighbors = List.of(
                    new Position(position.getX() + 1, position.getY()), // E
                    new Position(position.getX() + 1, position.getY() + 1), // NE
                    new Position(position.getX() + 1, position.getY() - 1) // SE
            );
            case WEST -> neighbors = List.of(
                    new Position(position.getX() - 1, position.getY()), // W
                    new Position(position.getX() - 1, position.getY() + 1), // NE
                    new Position(position.getX() - 1, position.getY() - 1) // SW
            );
        }
        for (Position neighborPosition : neighbors) {
            if (positions.contains(neighborPosition)) {
                return false;
            }
        }
        return true;
    }

    public int getTotalArea() {
        int minX = elves.stream().mapToInt(elve -> elve.getPosition().getX()).min().orElseThrow() - 1;
        int maxX = elves.stream().mapToInt(elve -> elve.getPosition().getX()).max().orElseThrow();
        int minY = elves.stream().mapToInt(elve -> elve.getPosition().getY()).min().orElseThrow() - 1;
        int maxY = elves.stream().mapToInt(elve -> elve.getPosition().getY()).max().orElseThrow();

        return (maxX - minX) * (maxY - minY) - elves.size();
    }
}
