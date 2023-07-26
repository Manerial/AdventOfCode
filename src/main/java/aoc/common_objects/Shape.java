package aoc.common_objects;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Shape {
    private final Set<Position> positions;
    private int id;

    public Shape(int id) {
        this.positions = new HashSet<>();
        this.id = id;
    }

    public Shape(Set<Position> positions) {
        this.positions = positions;
    }

    public Shape copyWithOffset(int x, int y) {
        Set<Position> copyPositions = this.positions.stream()
                .map(position -> new Position(position.getX() + x, position.getY() + y))
                .collect(Collectors.toSet());
        return new Shape(copyPositions);
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
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                positions.add(new Position(x, y));
            }
        }
    }
}
