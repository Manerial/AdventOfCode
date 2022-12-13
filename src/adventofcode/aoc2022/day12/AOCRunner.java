package adventofcode.aoc2022.day12;

import template.AOC;
import utilities.*;

import java.io.IOException;
import java.util.List;

/**
 * AdventOfCode 2022 day 12's instructions are <a href="https://adventofcode.com/2022/day/12">here</a>
 */
public class AOCRunner implements AOC {

    private final HeightMap grid = new HeightMap();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            Cell lastCell = initiateGridGetLastCell(list);
            grid.calculateGridFromStartAndEnd();
            Printer.println(grid);
            grid.setFromStart(true);
            Printer.println(grid);
            Printer.println("Solution 1 :" + ((lastCell != null) ? lastCell.getDistanceFromStart() : -1));

            int minDistanceFromEnd = grid.getGrid().values().stream()
                    .filter(cell -> cell.getHeight() == 'a')
                    .map(Cell::getDistanceFromEnd)
                    .filter(integer -> integer > -1)
                    .reduce(Integer::min)
                    .orElse(0);
            Printer.println("Solution 2 :" + minDistanceFromEnd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cell initiateGridGetLastCell(List<String> list) {
        Cell lastCell = null;
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
                    lastCell = cell;
                }
                x++;
            }
            y++;
        }
        return lastCell;
    }
}
