package aoc.exercises.year2024.day06;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.Getter;
import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class InputParser extends AbstractInputParser<Map<Position, Character>> {
    private Position currentPosition;
    private Direction currentDirection;

    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Position, Character> parseInput() {
        Map<Position, Character> map = new HashMap<>();
        for (int y = 0; y < inputList.size(); y++) {
            String line = inputList.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Position position = new Position(x, y);
                map.put(position, c);
                if (c == 'v' || c == '^' || c == '<' || c == '>') {
                    currentPosition = position;
                    currentDirection = Direction.charToDirection(c);
                }
            }
        }
        return map;
    }
}
