package aoc.exercises.year2022.day14;

import lombok.Setter;
import aoc.common_objects.Position;

import java.util.HashMap;
import java.util.Map;

public class Cavern {
    private final Map<Position, Block> grid = new HashMap<>();
    @Setter
    private Position dropPosition;
    private final Position positionMin;
    private final Position positionMax;


    public Cavern(int deep) {
        positionMin = new Position(0, 0);
        positionMax = new Position(1000, deep + 2);
    }

    public void fillWithAir() {
        for (int x = positionMin.getX(); x <= positionMax.getX(); x++) {
            for (int y = positionMin.getY(); y <= positionMax.getY(); y++) {
                grid.put(new Position(x, y), Block.AIR);
            }
        }
    }

    public void fillBottomWithRocks() {
        Position bottomLeft = new Position(positionMin.getX(), positionMax.getY());
        addRocks(bottomLeft, positionMax);
    }

    public void addRocks(Position position1, Position position2) {
        Position.interval(position1, position2)
                .forEach(position -> grid.replace(position, Block.ROCK));
    }

    public void dropSandUntilAbyss() {
        boolean reachAbyss = false;
        while (!reachAbyss) {
            reachAbyss = dropSand();
        }
    }

    public void dropSandUntilFull() {
        while (!isFull()) {
            dropSand();
        }
    }

    public boolean dropSand() {
        Position currentPosition; // sand has not spawned yet
        Position nextPosition = new Position(dropPosition);

        do {
            currentPosition = nextPosition;
            nextPosition = getNextPosition(currentPosition);
        } while (nextPosition != null && !nextPosition.equals(currentPosition));

        if (nextPosition == currentPosition) {
            grid.put(currentPosition, Block.SAND);
            return false;
        }
        return true;
    }

    private Position getNextPosition(Position position) {
        Position bottom = new Position(position);
        bottom.incY();
        Position bottomLeft = new Position(bottom);
        Position bottomRight = new Position(bottom);
        bottomLeft.decX();
        bottomRight.incX();

        if (reachAbyss(bottom)) {
            return null;
        } else if (containsAir(bottom)) {
            return bottom;
        } else if (containsAir(bottomLeft)) {
            return bottomLeft;
        } else if (containsAir(bottomRight)) {
            return bottomRight;
        }
        return position;
    }

    private boolean reachAbyss(Position bottom) {
        return !grid.containsKey(bottom);
    }

    private boolean containsAir(Position position) {
        return grid.get(position) == Block.AIR;
    }

    public long countSand() {
        return grid.values().stream().filter(character -> character == Block.SAND).count();
    }

    public boolean isFull() {
        return grid.get(dropPosition) == Block.SAND;
    }

}
