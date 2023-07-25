package aoc.exercises.year2022.day15;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import aoc.common_objects.Position;
import org.apache.commons.lang3.tuple.ImmutablePair;

@RequiredArgsConstructor
public class Sensor {
    private final Position position;
    @Getter
    private final Position nearestBeaconPosition;

    public ImmutablePair<Position, Position> scanEmptyLine(int yCoordinate) {
        int manhattanDistance = position.getManhattanDistance(nearestBeaconPosition);
        if(yCoordinate < position.getY() - manhattanDistance || yCoordinate > position.getY() + manhattanDistance) {
            return null;
        }
        int distance = manhattanDistance - Math.abs(position.getY() - yCoordinate);
        int minX = position.getX() - distance;
        int maxX = position.getX() + distance;

        Position begin = new Position(minX, yCoordinate);
        Position end = new Position(maxX, yCoordinate);
        return new ImmutablePair<>(begin, end);
    }
}
