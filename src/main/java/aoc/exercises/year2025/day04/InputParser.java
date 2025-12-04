package aoc.exercises.year2025.day04;

import aoc.common_objects.*;
import utilities.*;

import java.util.*;

public class InputParser extends AbstractInputParser<Map<Position, Roll>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Position, Roll> parseInput() {
        Map<Position, Roll> rolls = new HashMap<>();
        for (int y = 0; y < inputList.size(); y++) {
            String line = inputList.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '@') {
                    Roll roll = new Roll(new Position(x, y));
                    rolls.put(roll.getPosition(), roll);
                }
            }
        }
        return populateNeighbors(rolls);
    }

    private Map<Position, Roll> populateNeighbors(Map<Position, Roll> rolls) {
        rolls.forEach((position, roll) -> {
            List<Position> neighborPositions = position.getAllNeighbors();
            neighborPositions.forEach(neighborPosition -> {
                if (rolls.containsKey(neighborPosition)) {
                    rolls.get(neighborPosition).addNeighbor(roll);
                }
            });
        });
        return rolls;
    }
}
