package aoc.exercises.year2024.day14;

import aoc.common_objects.Position;
import lombok.Getter;

@Getter
public class Guard {
    private Position position;
    private final Position velocity;

    public Guard(String line) {
        String[] parts = line.split(" ");

        this.position = new Position(parts[0].substring(2), ",");
        this.velocity = new Position(parts[1].substring(2), ",");
    }

    public void move(int movements, int maxX, int maxY) {
        int newX = (position.getX() + velocity.getX() * movements) % maxX;
        if (newX < 0) {
            newX += maxX;
        }
        int newY = (position.getY() + velocity.getY() * movements) % maxY;
        if (newY < 0) {
            newY += maxY;
        }
        position = new Position(newX, newY);
    }
}
