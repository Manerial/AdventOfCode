package aoc.exercises.year2023.day10;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<Position, Character>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Position, Character> parseInput() {
        int maxX = inputList.get(0).length();
        int maxY = inputList.size();
        Map<Position, Character> map = new HashMap<>();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char c = inputList.get(y).charAt(x);
                Position position = new Position(x, y);
                map.put(position, c);
            }
        }
        return map;
    }
}
