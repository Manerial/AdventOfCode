package adventofcode.aoc2022.day14;

import lombok.Setter;
import utilities.Position;

import java.util.HashMap;
import java.util.Map;

public class Cavern {
    private final Map<Position, Block> grid = new HashMap<>();
    @Setter
    private Position dropPosition;
    private final Position topLeft;
    private final Position bottomRight;


    public Cavern(int deep) {
        topLeft = new Position(0, 0);
        bottomRight = new Position(1000, deep + 2);
    }

    public void fillWithAir() {
        for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
            for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
                grid.put(new Position(x, y), Block.AIR);
            }
        }
    }

    public void fillBottomWithRocks() {
        Position bottomLeft = new Position(topLeft.getX(), bottomRight.getY());
        addRocks(bottomLeft, bottomRight);
    }

    public void addRocks(Position position1, Position position2) {
        Position.interval(position1, position2)
                .forEach(position -> grid.replace(position, Block.ROCK));
    }

    public void dropSandUntilAbyss() {
        boolean reachAbyss = true;
        while (reachAbyss) {
            reachAbyss = dropSand();
        }
    }

    public void dropSandUntilFull() {
        while (!isFull()) {
            dropSand();
        }
    }

    public boolean dropSand() {
        Position currentPosition = new Position(dropPosition);
        Position nextPosition = getNextPosition(currentPosition);

        while (nextPosition != null && !nextPosition.equals(currentPosition)) {
            currentPosition = nextPosition;
            nextPosition = getNextPosition(currentPosition);
        }

        if (nextPosition == null) {
            grid.put(currentPosition, Block.SAND);
            return true;
        }
        return false;
    }

    private Position getNextPosition(Position position) {
        Position p1 = new Position(position);
        Position p2 = new Position(position);
        Position p3 = new Position(position);
        p1.incY();
        p2.incY();
        p3.incY();
        p1.decX();
        p3.incX();
        if (grid.containsKey(p2) && grid.get(p2) == Block.AIR) {
            return p2;
        } else if (grid.containsKey(p1) && grid.get(p1) == Block.AIR) {
            return p1;
        } else if (grid.containsKey(p3) && grid.get(p3) == Block.AIR) {
            return p3;
        } else if(grid.containsKey(p1) || grid.containsKey(p2) || grid.containsKey(p3)) {
            return null;
        }
        return position;
    }

    public long countSand() {
       return grid.values().stream().filter(character -> character == Block.SAND).count();
    }

    public boolean isFull() {
        return grid.get(dropPosition) == Block.SAND;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
            for (Position position : Position.interval(new Position(topLeft.getX(), y), new Position(bottomRight.getX(), y))) {
                stringBuilder.append(grid.get(position));
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}
