package aoc.exercises.year2022.day22;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<Grid> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Grid parseInput() {
        Grid grid = new Grid();
        for (int y = 1; y <= inputList.size() - 2; y++) {
            String line = inputList.get(y - 1);
            for (int x = 1; x <= line.length(); x++) {
                char lineChar = line.charAt(x - 1);
                if (lineChar != ' ') {
                    grid.add(new Position(x, y), lineChar);
                }
            }
        }

        grid.setMoves(inputList.get(inputList.size() - 1));

        int lines = inputList.size() - 2;
        int cols = inputList.stream().limit(inputList.size() - 2L).mapToInt(String::length).max().orElseThrow();
        int faceSize = (lines + cols) / 7;

        Cube cube = new Cube(faceSize, grid);

        grid.setCube(cube);
        return grid;
    }
}
