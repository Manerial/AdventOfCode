package aoc.exercises.year2022.day23;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import aoc.common_objects.RoundDeque;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {
    private final List<Elve> elves = new ArrayList<>();
    private Set<Position> elvesPositions = new HashSet<>();

    protected static final RoundDeque<Direction> directions = new RoundDeque<>(
            List.of(
                    Direction.NORTH,
                    Direction.SOUTH,
                    Direction.WEST,
                    Direction.EAST
            ));

    public void addElve(Position position, Elve elve) {
        elvesPositions.add(position);
        elves.add(elve);
    }

    private void roundDirections() {
        directions.rotate(1L);
    }

    public void move() {
        elves.forEach(elve -> elve.setAllowedMoves(getAllowedMoves(elve)));
        elves.parallelStream()
                .filter(Elve::canMove)
                .forEach(this::targetPosition);

        elves.parallelStream()
                .filter(Elve::canMove)
                .forEach(elve -> elve.moveIfPossible(elves));

        elvesPositions = elves.parallelStream()
                .map(Elve::getPosition)
                .collect(Collectors.toSet());

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
        return elves.parallelStream()
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
        return elve.getPosition().getNeighbors(direction).stream()
                .noneMatch(elvesPositions::contains);
    }

    public int getTotalArea() {
        int minX = elves.stream().mapToInt(elve -> elve.getPosition().getX()).min().orElseThrow() - 1;
        int maxX = elves.stream().mapToInt(elve -> elve.getPosition().getX()).max().orElseThrow();
        int minY = elves.stream().mapToInt(elve -> elve.getPosition().getY()).min().orElseThrow() - 1;
        int maxY = elves.stream().mapToInt(elve -> elve.getPosition().getY()).max().orElseThrow();

        return (maxX - minX) * (maxY - minY) - elves.size();
    }
}
