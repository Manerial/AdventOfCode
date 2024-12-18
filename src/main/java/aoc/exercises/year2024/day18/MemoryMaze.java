package aoc.exercises.year2024.day18;

import aoc.common_objects.Position;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MemoryMaze {
    private Map<Position, Integer> memoryMap = new HashMap<>();
    private Position start;
    private Position end;

    public void init(int mapSize, List<Position> forbiden) {
        start = new Position(0, 0);
        end = new Position(mapSize - 1, mapSize - 1);

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                memoryMap.putIfAbsent(new Position(x, y), Integer.MAX_VALUE);
            }
        }
        forbiden.forEach(this::corrupt);
    }

    public int reachEnd() {
        runMap(start, 0);
        return memoryMap.get(end);
    }

    private void runMap(Position position, int steps) {
        if (memoryMap.get(position) > steps) {
            memoryMap.put(position, steps);
        } else {
            return;
        }
        List<Position> neighborsInMap = position.getDirectNeighbors().stream()
                .filter(memoryMap::containsKey)
                .toList();
        for (Position neighbor : neighborsInMap) {
            runMap(neighbor, steps + 1);
        }
    }

    public void removeAlonePaths() {
        List<Position> positions = new ArrayList<>(memoryMap.keySet());
        positions.forEach(this::corruptIfAlone);
    }

    private void corruptIfAlone(Position position) {
        if (position.equals(start) || position.equals(end)) {
            return;
        }
        List<Position> neighbors = position.getDirectNeighbors().stream()
                .filter(memoryMap::containsKey)
                .toList();
        if (neighbors.size() <= 1) {
            corrupt(position);
            if (neighbors.size() == 1) {
                corruptIfAlone(neighbors.get(0));
            }
        }
    }

    public void corrupt(Position corruptedPosition) {
        memoryMap.remove(corruptedPosition);
    }

    public boolean canReachEnd() {
        return end.getDirectNeighbors().stream()
                .anyMatch(memoryMap::containsKey);
    }
}
