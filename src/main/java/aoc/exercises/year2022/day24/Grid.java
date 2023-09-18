package aoc.exercises.year2022.day24;

import aoc.common_objects.Position;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private int width;
    private int height;
    private Position startPos;
    private Position endPos;
    private Map<Position, Cell> map = new HashMap<>();

    public void cleanAndReverseGoal() {
        map.values().forEach(Cell::unPopulate);
        Position newStartPos = endPos;
        endPos = startPos;
        startPos = newStartPos;
        map.get(startPos).populate();
    }

    public void addCell(Position position, Cell cell) {
        map.put(position, cell);
    }

    public void populateStart() {
        startPos = getCellAtYThatIsNotWall(0);
        map.get(startPos).populate();
    }

    public void computeEnds() {
        width = map.keySet().stream()
                .max(Comparator.comparingInt(Position::getX))
                .orElseThrow()
                .getX() + 1;
        height = map.keySet().stream()
                .max(Comparator.comparingInt(Position::getY))
                .orElseThrow()
                .getY() + 1;
        endPos = getCellAtYThatIsNotWall(height - 1);
    }

    public void blowOnMap() {
        Map<Position, Cell> mapWithoutWind = cloneMapWithoutWind();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position position = new Position(x, y);
                Cell cell = map.get(position);
                blowOnCell(position, cell, mapWithoutWind);
            }
        }
        this.map = mapWithoutWind;
    }

    private void blowOnCell(Position position, Cell cell, Map<Position, Cell> map) {
        cell.getWinds().forEach(wind -> {
            Position targetPosition = new Position(position);
            switch (wind) {
                case 'v' -> targetPosition.incY();
                case '^' -> targetPosition.decY();
                case '<' -> targetPosition.decX();
                case '>' -> targetPosition.incX();
                default -> throw new IllegalArgumentException(wind + " is not a wind type");
            }
            loopIfNeeded(targetPosition);
            map.get(targetPosition).addWind(wind);
        });

    }

    private void loopIfNeeded(Position targetPosition) {
        int maxX = width - 1;
        int maxY = height - 1;
        if (targetPosition.getX() == maxX) {
            targetPosition.setX(1);
        } else if (targetPosition.getX() == 0) {
            targetPosition.setX(maxX - 1);
        } else if (targetPosition.getY() == maxY) {
            targetPosition.setY(1);
        } else if (targetPosition.getY() == 0) {
            targetPosition.setY(maxY - 1);
        }
    }

    private Map<Position, Cell> cloneMapWithoutWind() {
        Map<Position, Cell> newMap = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position position = new Position(x, y);
                Cell oldCell = map.get(position);
                Cell newCell = oldCell.cloneCellWithoutWind();
                newMap.put(position, newCell);
            }
        }
        return newMap;
    }

    public void populate() {
        List<Position> populatedPositions = map.entrySet().stream()
                .filter(entry -> entry.getValue().isPopulated())
                .map(Map.Entry::getKey)
                .toList();

        for (Position position : populatedPositions) {
            Position up = new Position(position);
            Position down = new Position(position);
            Position left = new Position(position);
            Position right = new Position(position);

            up.incY();
            down.decY();
            left.decX();
            right.incX();

            populateAtPos(up);
            populateAtPos(down);
            populateAtPos(left);
            populateAtPos(right);

            Cell currentCell = map.get(position);
            if (currentCell.hasWind()) {
                currentCell.unPopulate();
            }
        }
    }

    private void populateAtPos(Position position) {
        Cell cell = map.get(position);
        if (cell != null && !cell.isWall() && !cell.hasWind()) {
            cell.populate();
        }
    }

    public boolean isFinished() {
        return map.get(endPos).isPopulated();
    }

    private Position getCellAtYThatIsNotWall(int minPos) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey().getY() == minPos && !entry.getValue().isWall())
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
    }
}
