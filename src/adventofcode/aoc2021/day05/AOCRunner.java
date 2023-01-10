package adventofcode.aoc2021.day05;

import template.AOC;
import utilities.FileLoader;
import utilities.Position;
import utilities.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdventOfCode 2021 day 5's instructions are <a href="https://adventofcode.com/2021/day/5">here</a>
 */
public class AOCRunner implements AOC {
    private final Map<Position, Integer> gridLines = new HashMap<>();
    private final Map<Position, Integer> gridLinesAndDiagonals = new HashMap<>();

    @Override
    public void run(String file) throws IOException {
        List<String> list = FileLoader.readListFromFile(file);
        for (String item : list) {
            String part1 = item.split(" -> ")[0];
            String part2 = item.split(" -> ")[1];
            Position p1 = parsePosition(part1);
            Position p2 = parsePosition(part2);
            mapLine(p1, p2);
            mapLineAndDiagonal(p1, p2);
        }
        Printer.println("Solution 1 : " + gridLines.values().stream().filter(integer -> integer >= 2).count());
        Printer.println("Solution 2 : " + gridLinesAndDiagonals.values().stream().filter(integer -> integer >= 2).count());
    }

    private Position parsePosition(String part1) {
        int x = Integer.parseInt(part1.split(",")[0].trim());
        int y = Integer.parseInt(part1.split(",")[1].trim());
        return new Position(x, y);
    }

    private void mapLineAndDiagonal(Position p1, Position p2) {
        for (Position position : Position.interval(p1, p2)) {
            gridLinesAndDiagonals.compute(position, (p, integer) -> (integer == null) ? 1 : integer + 1);
        }
    }

    private void mapLine(Position p1, Position p2) {
        if (Position.isLine(p1, p2)) {
            for (Position position : Position.interval(p1, p2)) {
                gridLines.compute(position, (p, integer) -> (integer == null) ? 1 : integer + 1);
            }
        }
    }
}
