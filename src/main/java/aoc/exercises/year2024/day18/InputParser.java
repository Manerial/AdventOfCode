package aoc.exercises.year2024.day18;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<Position>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Position> parseInput() {
        List<Position> positions = new ArrayList<>();
        for (String line : inputList) {
            positions.add(new Position(line, ","));
        }
        return positions;
    }
}
