package aoc.exercises.year2022.day17;


import aoc.common_objects.Position;
import aoc.common_objects.Shape;

import java.util.List;

public enum RockShape {
    R1, R2, R3, R4, R5;

    private final Shape shape;

    RockShape() {
        this.shape = createShape(this);
    }

    public Shape spawnRock(int xOffset, int yOffset) {
        return shape.copyWithOffset(xOffset, yOffset);
    }

    public RockShape next() {
        return switch (this) {
            case R1 -> R2;
            case R2 -> R3;
            case R3 -> R4;
            case R4 -> R5;
            case R5 -> R1;
        };
    }

    public Shape createShape(RockShape rockShape) {
        switch (rockShape.name()) {
            case "R1" -> {
                return asShape(List.of(
                        new Position(0, 0),
                        new Position(3, 0)
                ));
            }
            case "R2" -> {
                return asShape(List.of(
                        new Position(1, 0),
                        new Position(1, 2),
                        new Position(0, 1),
                        new Position(2, 1)
                ));
            }
            case "R3" -> {
                return asShape(List.of(
                        new Position(0, 0),
                        new Position(2, 0),
                        new Position(2, 2)));
            }
            case "R4" -> {
                return asShape(List.of(
                        new Position(0, 0),
                        new Position(0, 3)));
            }
            case "R5" -> {
                return asShape(List.of(
                        new Position(0, 0),
                        new Position(0, 1),
                        new Position(1, 1),
                        new Position(1, 0)));
            }
            default -> throw new IllegalArgumentException("This shape is not defined : " + rockShape.name());
        }
    }

    private Shape asShape(List<Position> positions) {
        return new Shape(Position.shape(positions));
    }
}
