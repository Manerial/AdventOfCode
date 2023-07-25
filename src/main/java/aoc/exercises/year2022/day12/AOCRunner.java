package aoc.exercises.year2022.day12;

import utilities.AbstractAOC;
import aoc.common_objects.Position;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/12">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    private final HeightMap grid = new HeightMap();

    @Override
    public void run() {
        initiateGridGetLastCell(inputList);
        grid.calculateGridFromStartAndEnd();
        solution1 = grid.getLastCell().getDistanceFromStart();

        solution2 = grid.getNearestCell().getDistanceFromEnd();
    }

    private void initiateGridGetLastCell(List<String> list) {
        int y = 0;
        for (String line : list) {
            int x = 0;
            for (char c : line.toCharArray()) {
                Position position = new Position(x, y);
                Cell cell = new Cell(position, c);
                grid.addCell(position, cell);
                if (c == 'S') {
                    cell.setHeight('a');
                    cell.setDistance(0, true);
                }
                if (c == 'E') {
                    cell.setHeight('z');
                    cell.setDistance(0, false);
                }
                x++;
            }
            y++;
        }
    }
}
