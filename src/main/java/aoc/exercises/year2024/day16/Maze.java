package aoc.exercises.year2024.day16;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Maze {
    private final Map<Position, Integer> pathScore;
    private final Position start;
    private final Position end;

    public void computePathScore() {
        computePathScore(start, Direction.EAST, 0);
    }

    public int getPathToEndValue() {
        return pathScore.get(end);
    }

    public int countTilesOnCriticalPath() {
        Set<Position> criticalPath = new HashSet<>();
        getTilesOnCriticalPath(end, null, criticalPath);
        return criticalPath.size();
    }

    private void getTilesOnCriticalPath(Position position, Direction direction, Set<Position> criticalPath) {
        int positionScore = pathScore.get(position);
        criticalPath.add(position);
        for (Position neighbor : position.getDirectNeighbors()) {
            if (!pathScore.containsKey(neighbor)) {
                continue;
            }
            Direction neighborDirection = Position.directionFromTo(position, neighbor);
            int neighborScore = pathScore.get(neighbor);
            neighborScore = getCorrectedScore(positionScore, neighborScore, direction, neighborDirection);
            if (positionScore > neighborScore) {
                getTilesOnCriticalPath(neighbor, neighborDirection, criticalPath);
            }
        }
    }

    private int getCorrectedScore(int positionScore, int neighborScore, Direction direction, Direction neighborDirection) {
        if (direction == neighborDirection && neighborScore - 999 == positionScore) {
            neighborScore -= 1000;
        }
        return neighborScore;
    }

    private void computePathScore(Position currentPosition, Direction currentDirection, int steps) {
        int currentPositionScore = pathScore.get(currentPosition);
        if (currentPositionScore <= steps) {
            return;
        }
        pathScore.put(currentPosition, steps);

        List<Position> neighborsInMaze = getNeighborsInMaze(currentPosition);
        for (Position neighbor : neighborsInMaze) {
            Direction nextDirection = Position.directionFromTo(currentPosition, neighbor);
            if (nextDirection == null) {
                throw new IllegalArgumentException("Invalid direction from " + currentPosition + " to " + neighbor);
            }
            if (nextDirection.isOpposite(currentDirection)) {
                continue;
            }
            int nextSteps = steps;
            if (nextDirection != currentDirection) {
                nextSteps += 1000;
            }
            computePathScore(neighbor, nextDirection, nextSteps + 1);
        }
    }


    private List<Position> getNeighborsInMaze(Position currentPosition) {
        return currentPosition.getDirectNeighbors().stream()
                .filter(pathScore::containsKey)
                .toList();
    }
}
