package aoc.exercises.year2023.day03;

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
        Map<Position, Character> positions = new HashMap<>();
        for (int lineIndex = 0; lineIndex < inputList.size(); lineIndex++) {
            String line = inputList.get(lineIndex);
            for (int charIndex = 0; charIndex < line.length(); charIndex++) {
                positions.put(new Position(lineIndex, charIndex), line.charAt(charIndex));
            }
        }
        return positions;
    }
}
