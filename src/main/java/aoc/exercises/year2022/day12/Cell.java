package aoc.exercises.year2022.day12;

import lombok.Data;
import aoc.common_objects.Position;

@Data
public class Cell {
    private Position position;
    private char height;
    private int distanceFromStart = -1;
    private int distanceFromEnd = -1;
    public Cell(Position position, char height) {
        this.position = position;
        this.height = height;
    }

    public char getHeight() {
        return height;
    }

    public int getDistance(boolean fromStart) {
        return (fromStart) ? distanceFromStart : distanceFromEnd;
    }

    public void setDistance(int distance, boolean fromStart) {
        if (fromStart) {
            distanceFromStart = distance;
        } else {
            distanceFromEnd = distance;
        }
    }
}