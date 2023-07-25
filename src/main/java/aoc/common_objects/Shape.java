package aoc.common_objects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Shape {
    private final List<Position> positions;
    private int id;
    public Shape(int id) {
        this.positions = new ArrayList<>();
        this.id = id;
    }

    public Shape(List<Position> positions) {
        this.positions = positions;
    }

    public Shape copy() {
        List<Position> copyPositions = new ArrayList<>();
        for (Position position : this.positions) {
            copyPositions.add(new Position(position));
        }
        return new Shape(copyPositions);
    }

    public void initPosition(int x, int y) {
        for(Position position : positions) {
            position.setX(position.getX() + x);
            position.setY(position.getY() + y);
        }
    }

    public int getMinY() {
        return positions.stream()
                .map(Position::getY)
                .min(Integer::compareTo)
                .orElse(0);
    }

    public int getMinX() {
        return positions.stream()
                .map(Position::getX)
                .min(Integer::compareTo)
                .orElse(0);
    }

    public int getMaxX() {
        return positions.stream()
                .map(Position::getX)
                .max(Integer::compareTo)
                .orElse(0);
    }

    public void setRectangle(Position start, int width, int height) {
        Position to = new Position(start);
        to.setX(to.getX() + width - 1);
        to.setY(to.getY() + height - 1);
        setRectangle(start, to);
    }

    public void setRectangle(Position from, Position to) {
        for(int x = from.getX(); x <= to.getX(); x++) {
            for(int y = from.getY(); y <= to.getY(); y++) {
                positions.add(new Position(x, y));
            }
        }
    }
}
