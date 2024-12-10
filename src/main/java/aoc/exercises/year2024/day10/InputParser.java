package aoc.exercises.year2024.day10;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<Position, Integer>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Position, Integer> parseInput() {
        Map<Position, Integer> map = new HashMap<>();
        for (int y = 0; y < inputList.size(); y++) {
            for (int x = 0; x < inputList.get(y).length(); x++) {
                map.put(new Position(x, y), Character.getNumericValue(inputList.get(y).charAt(x)));
            }
        }
        return map;
    }
}
