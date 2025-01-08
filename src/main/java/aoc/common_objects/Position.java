package aoc.common_objects;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Data
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String coordinates, String splitter) {
        x = Integer.parseInt(coordinates.split(splitter)[0]);
        y = Integer.parseInt(coordinates.split(splitter)[1]);
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public void setPosition(Position nextPosition) {
        this.x = nextPosition.x;
        this.y = nextPosition.y;
    }

    public void incX() {
        x++;
    }

    public void decX() {
        x--;
    }

    public void incY() {
        y++;
    }

    public void decY() {
        y--;
    }

    /**
     * If p1 and p2 are on the same line/column/diagonal
     * return all the positions between them (included)
     *
     * @param p1 The first position
     * @param p2 The second position
     * @return all the position between p1 and p2 (included)
     */
    public static List<Position> interval(Position p1, Position p2) {
        List<Position> positions = new ArrayList<>();
        List<Integer> rangeX = Range.toList(p1.x, p2.x);
        List<Integer> rangeY = Range.toList(p1.y, p2.y);
        if (isLine(p1, p2)) { // Line or column
            rangeX.forEach(x -> positions.add(new Position(x, p1.y)));
            rangeY.forEach(y -> positions.add(new Position(p1.x, y)));
        } else if (isDiagonal(p1, p2)) {
            for (int i = 0; i < rangeX.size(); i++) {
                Position position = new Position(rangeX.get(i), rangeY.get(i));
                positions.add(position);
            }
        }
        return new ArrayList<>(positions.stream().distinct().toList());
    }

    public static Set<Position> shape(List<Position> positions) {
        Set<Position> shape = new HashSet<>();
        for (Position positionA : positions) {
            int indexOfB = positions.indexOf(positionA) + 1;
            Position positionB = (indexOfB < positions.size()) ? positions.get(indexOfB) : positionA;
            shape.addAll(Position.interval(positionA, positionB));
        }
        return shape;
    }

    public static boolean isDiagonal(Position position1, Position position2) {
        int deltaX = Math.abs(position1.getX() - position2.getX());
        int deltaY = Math.abs(position1.getY() - position2.getY());
        return deltaX == deltaY;
    }

    public static boolean isLine(Position position1, Position position2) {
        return position1.getX() == position2.getX() || position1.getY() == position2.getY();
    }

    public Position maxComparator(Position position) {
        if (this.x * this.y < position.x * position.y) {
            return position;
        }
        return this;
    }

    public static Integer minFromOrigin(Position a, Position b) {
        return a.getDistanceFrom0() - b.getDistanceFrom0();
    }

    public int getManhattanDistance(Position position) {
        return getManhattanX(position) + getManhattanY(position);
    }

    public int getManhattanY(Position position) {
        return Math.abs(y - position.y);
    }

    public int getManhattanX(Position position) {
        return Math.abs(x - position.x);
    }

    public void move(Direction cardinal) {
        switch (cardinal) {
            case EAST -> x++;
            case WEST -> x--;
            case NORTH -> y--;
            case SOUTH -> y++;
        }
    }

    public void move(Direction cardinal, int steps) {
        switch (cardinal) {
            case EAST -> x += steps;
            case WEST -> x -= steps;
            case NORTH -> y -= steps;
            case SOUTH -> y += steps;
        }
    }

    public int getDistanceFrom0() {
        return Math.abs(x) + Math.abs(y);
    }

    public List<Position> getAllNeighbors() {
        List<Position> neighbors = new ArrayList<>();
        neighbors.addAll(getDirectNeighbors());
        neighbors.addAll(getDiagNeighbors());
        return neighbors;
    }

    public List<Position> getDirectNeighbors() {
        return List.of(
                getNorth(),
                getSouth(),
                getEast(),
                getWest()
        );
    }

    public List<Position> getDiagNeighbors() {
        return List.of(
                new Position(x - 1, y - 1),
                new Position(x + 1, y - 1),
                new Position(x - 1, y + 1),
                new Position(x + 1, y + 1)
        );
    }

    public Position getNeighbor(Direction direction) {
        return switch (direction) {
            case NORTH -> getNorth();
            case EAST -> getEast();
            case SOUTH -> getSouth();
            case WEST -> getWest();
        };
    }

    public List<Position> getNeighbors(Direction direction) {
        return switch (direction) {
            case NORTH -> List.of(
                    new Position(x, y - 1), // N
                    new Position(x + 1, y - 1), // NE
                    new Position(x - 1, y - 1) // NW
            );
            case SOUTH -> List.of(
                    new Position(x, y + 1), // S
                    new Position(x + 1, y + 1), // SE
                    new Position(x - 1, y + 1) // SW
            );
            case EAST -> List.of(
                    new Position(x + 1, y), // E
                    new Position(x + 1, y + 1), // NE
                    new Position(x + 1, y - 1) // SE
            );
            case WEST -> List.of(
                    new Position(x - 1, y), // W
                    new Position(x - 1, y + 1), // NW
                    new Position(x - 1, y - 1) // SW
            );
        };
    }

    public Position getNorth() {
        return new Position(x, y - 1);
    }

    public Position getSouth() {
        return new Position(x, y + 1);
    }

    public Position getEast() {
        return new Position(x + 1, y);
    }

    public Position getWest() {
        return new Position(x - 1, y);
    }

    public static Direction directionFromTo(Position from, Position to) {
        if (from.x == to.x) {
            return from.y > to.y ? Direction.NORTH : Direction.SOUTH;
        } else if (from.y == to.y) {
            return from.x > to.x ? Direction.WEST : Direction.EAST;
        }
        return null;
    }

    public static List<Position> pathTo(Position startPosition, Position targetPosition, boolean leftFirst) {
        AtomicPosition currentPosition = new AtomicPosition(startPosition);
        AtomicPosition finalPosition = new AtomicPosition(targetPosition);
        List<Position> path = new ArrayList<>();
        path.add(new Position(startPosition));
        if (leftFirst) {
            path.addAll(getPath(currentPosition, finalPosition, AtomicPosition::getX));
            path.addAll(getPath(currentPosition, finalPosition, AtomicPosition::getY));
        } else {
            path.addAll(getPath(currentPosition, finalPosition, AtomicPosition::getY));
            path.addAll(getPath(currentPosition, finalPosition, AtomicPosition::getX));
        }
        return path;
    }

    private static List<Position> getPath(AtomicPosition currentPosition, AtomicPosition targetPosition, Function<AtomicPosition, AtomicInteger> coordinate) {
        List<Position> path = new ArrayList<>();
        while (coordinate.apply(currentPosition).get() != coordinate.apply(targetPosition).get()) {
            if (coordinate.apply(currentPosition).get() < coordinate.apply(targetPosition).get()) {
                coordinate.apply(currentPosition).incrementAndGet();
            } else {
                coordinate.apply(currentPosition).decrementAndGet();
            }
            path.add(new Position(currentPosition.getX().get(), currentPosition.getY().get()));
        }
        return path;
    }
}
