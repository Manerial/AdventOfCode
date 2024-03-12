package aoc.exercises.year2018.day10;

import aoc.common_objects.Position;

/**
 * @param velocity Can be considered as a vector from 0:0
 */
public record Star(Position position, Position velocity) {
    public void move() {
        this.position.setX(this.position.getX() + this.velocity.getX());
        this.position.setY(this.position.getY() + this.velocity.getY());
    }
}
