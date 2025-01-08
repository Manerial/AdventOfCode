package aoc.common_objects;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class AtomicPosition {
    private AtomicInteger x;
    private AtomicInteger y;

    public AtomicPosition(Position position) {
        this.x = new AtomicInteger(position.getX());
        this.y = new AtomicInteger(position.getY());
    }
}
