package adventofcode.aoc2022.day15;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import utilities.Position;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Sensor {
    private final Position position;
    private final Position nearestBeaconPosition;
    private List<ImmutablePair<Position, Position>> emptyZones = new ArrayList<>();

    @Override
    public String toString() {
        return this.position.toString() + "; Sensors at : " + nearestBeaconPosition;
    }

    public ImmutablePair<Position, Position> scanEmptyLine(int yCoordinate) {
        int manathanDistance = position.getManhattanDistance(nearestBeaconPosition);
        if(yCoordinate < position.getY() - manathanDistance || yCoordinate > position.getY() + manathanDistance) {
            return null;
        }
        int distance = manathanDistance - Math.abs(position.getY() - yCoordinate);
        int minX = position.getX() - distance;
        int maxX = position.getX() + distance;

        Position begin = new Position(minX, yCoordinate);
        Position end = new Position(maxX, yCoordinate);
        return new ImmutablePair<>(begin, end);
    }
}
