package aoc.exercises.year2024.day13;

import aoc.common_objects.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PrizeCoordinates {
    long x;
    long y;

    public PrizeCoordinates(Position position) {
        if (position == null) {
            return;
        }
        this.x = position.getX();
        this.y = position.getY();
    }

    public void increment() {
        this.x += 10000000000000L;
        this.y += 10000000000000L;
    }
}
