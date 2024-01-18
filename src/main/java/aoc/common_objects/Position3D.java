package aoc.common_objects;

import lombok.*;

import java.util.Set;

@Data
public class Position3D {
    private int x;
    private int y;
    private int z;

    public Position3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position3D(String coordinates, String splitter) {
        x = Integer.parseInt(coordinates.split(splitter)[0]);
        y = Integer.parseInt(coordinates.split(splitter)[1]);
        z = Integer.parseInt(coordinates.split(splitter)[2]);
    }

    public Position3D(Position3D position) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    public void setPosition(Position3D nextPosition) {
        this.x = nextPosition.x;
        this.y = nextPosition.y;
        this.z = nextPosition.z;
    }

    public void incX() {
        x++;
    }

    public void decX() {
        x--;
    }

    public void incY() {
        y++;
    }

    public void decY() {
        y--;
    }

    public void incZ() {
        z++;
    }

    public void decZ() {
        z--;
    }

    public int getManhattanDistance(Position3D position) {
        return getManhattanX(position) + getManhattanY(position) + getManhattanZ(position);
    }

    public int getManhattanX(Position3D position) {
        return Math.abs(x - position.x);
    }

    public int getManhattanY(Position3D position) {
        return Math.abs(y - position.y);
    }

    public int getManhattanZ(Position3D position) {
        return Math.abs(z - position.z);
    }


    public double euclidianDistanceTo(Position3D position) {
        return Math.sqrt(Math.pow(x - position.x, 2) +
                Math.pow(y - position.y, 2) +
                Math.pow(z - position.z, 2));
    }

    public long countNeighbors(Set<Position3D> positions) {
        Set<Position3D> neighbors = getNeighbors();
        return neighbors.stream().filter(positions::contains).count();
    }

    public Set<Position3D> getNeighbors() {
        return Set.of(
                new Position3D(this.x + 1, this.y, this.z),
                new Position3D(this.x - 1, this.y, this.z),
                new Position3D(this.x, this.y + 1, this.z),
                new Position3D(this.x, this.y - 1, this.z),
                new Position3D(this.x, this.y, this.z + 1),
                new Position3D(this.x, this.y, this.z - 1)
        );

    }
}
