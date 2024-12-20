package aoc.exercises.year2024.day20;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<List<IndexedPosition>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<IndexedPosition> parseInput() {
        Map<Position, Character> map = toMap();
        List<IndexedPosition> iPositions = new ArrayList<>();
        Position current = getStartPosition(map);
        int index = 0;
        while (current != null) {
            iPositions.add(new IndexedPosition(current, index++));
            current = getNextPosition(map, current, iPositions, index);
        }
        return iPositions;
    }

    private Map<Position, Character> toMap() {
        Map<Position, Character> map = new HashMap<>();
        for (int y = 0; y < inputList.size(); y++) {
            String line = inputList.get(y);
            for (int x = 0; x < line.length(); x++) {
                Position position = new Position(x, y);
                char c = line.charAt(x);
                map.put(position, c);
            }
        }
        return map;
    }

    private static Position getStartPosition(Map<Position, Character> map) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() == 'S')
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
    }

    private static Position getNextPosition(Map<Position, Character> map, Position current, List<IndexedPosition> iPositions, int index) {
        return current.getDirectNeighbors().stream()
                .filter(map::containsKey)
                .filter(neighbor -> map.get(neighbor) != '#')
                .filter(neighbor -> notRegisteredAt(neighbor, iPositions, index - 2))
                .findAny()
                .orElse(null);
    }

    private static boolean notRegisteredAt(Position neighbor, List<IndexedPosition> iPositions, int index) {
        return iPositions.size() == 1 || !neighbor.equals(iPositions.get(index).position());
    }
}
