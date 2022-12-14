package adventofcode.aoc2022.day14;

import utilities.Position;

import java.util.HashMap;
import java.util.Map;

public class Cavern {
    private final Map<Position, Character> grid = new HashMap<>();
    private static final char ROCK = '#';
    private static final char AIR = ' ';
    private static final char SAND = 'O';
    private Position positionMin;
    private Position positionMax;

    public void fill(int minX, int minY, int maxX, int maxY) {
        positionMin = new Position(minX, minY);
        positionMax = new Position(maxX, maxY);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                grid.put(new Position(x, y), AIR);
            }
        }
    }

    public void fillBottomWithRocks() {
        int y = positionMax.getY();
        for (int x = positionMin.getX(); x <= positionMax.getX(); x++) {
                grid.put(new Position(x, y), ROCK);
        }
    }

    public void addRocks(Position position1, Position position2) {
        Position.interval(position1, position2).forEach(position -> grid.put(position, ROCK));
    }

    public boolean dropSand(Position position) {
        Position nextPosition = getNext(position);
        while (nextPosition != null && !nextPosition.equals(position)) {
            position = nextPosition;
            nextPosition = getNext(position);
        }
        if (nextPosition == null) {
            grid.put(position, SAND);
            return true;
        }
        return false;
    }

    private Position getNext(Position position) {
        Position p1 = new Position(position);
        Position p2 = new Position(position);
        Position p3 = new Position(position);
        p1.incY();
        p2.incY();
        p3.incY();
        p1.decX();
        p3.incX();
        if (grid.containsKey(p2) && grid.get(p2) == AIR) {
            return p2;
        } else if (grid.containsKey(p1) && grid.get(p1) == AIR) {
            return p1;
        } else if (grid.containsKey(p3) && grid.get(p3) == AIR) {
            return p3;
        } else if(grid.containsKey(p1) || grid.containsKey(p2) || grid.containsKey(p3)) {
            return null;
        }
        return position;
    }

    public long countSand() {
       return grid.values().stream().filter(character -> character == SAND).count();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = positionMin.getY(); y <= positionMax.getY(); y++) {
            for (Position position : Position.interval(new Position(positionMin.getX(), y), new Position(positionMax.getX(), y))) {
                stringBuilder.append(grid.get(position));
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public boolean isFull(Position dropPosition) {
        return grid.get(dropPosition) == SAND;
    }
}
