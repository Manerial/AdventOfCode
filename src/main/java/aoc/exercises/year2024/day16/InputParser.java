package aoc.exercises.year2024.day16;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Maze> {
    private List<Character> innerPath = List.of('.', 'S', 'E');

    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Maze parseInput() {
        Map<Position, Integer> pathScore = new HashMap<>();
        Position start = null;
        Position end = null;

        for (int y = 0; y < inputList.size(); y++) {
            for (int x = 0; x < inputList.get(y).length(); x++) {
                Position currentPosition = new Position(x, y);
                if (innerPath.contains(inputList.get(y).charAt(x))) {
                    pathScore.put(currentPosition, Integer.MAX_VALUE);
                    if (inputList.get(y).charAt(x) == 'S') {
                        start = currentPosition;
                    } else if (inputList.get(y).charAt(x) == 'E') {
                        end = currentPosition;
                    }
                }
            }
        }

        return new Maze(pathScore, start, end);
    }
}
