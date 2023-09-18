package aoc.exercises.year2022.day24;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<Grid> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Grid parseInput() {
        Grid grid = new Grid();

        int y = 0;
        for (String line : inputList) {
            int x = 0;
            for (char cellContent : line.toCharArray()) {
                Cell cell = new Cell(cellContent);
                Position position = new Position(x, y);
                grid.addCell(position, cell);
                x++;
            }
            y++;
        }

        grid.computeEnds();
        grid.populateStart();

        return grid;
    }

}
