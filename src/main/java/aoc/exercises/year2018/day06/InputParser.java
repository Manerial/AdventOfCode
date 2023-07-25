package aoc.exercises.year2018.day06;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<PositionsMinMax> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public PositionsMinMax parseInput() {
        List<Position> positions = inputList.stream().map(posStr -> new Position(posStr, ", "))
                .toList();
        int borneMin = getBorneMin(positions);
        int borneMax = getBorneMax(positions);
        return new PositionsMinMax(positions, borneMin, borneMax);
    }

    private static int getBorneMax(List<Position> positions) {
        return positions.stream()
                .mapToInt(position -> Math.max(position.getX(), position.getY()))
                .max()
                .orElseThrow();
    }

    private static int getBorneMin(List<Position> positions) {
        return positions.stream()
                .mapToInt(position -> Math.min(position.getX(), position.getY()))
                .min()
                .orElseThrow();
    }
}
