package aoc.exercises.year2024.day15;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.Data;

@Data
public class BigObject {
    private final Position position1;
    private final Position position2;

    public void move(Direction direction) {
        position1.move(direction);
        position2.move(direction);
    }
}
