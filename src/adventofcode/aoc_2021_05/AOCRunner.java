package adventofcode.aoc_2021_05;

import template.AOC;
import utilities.FileLoader;
import utilities.Position;
import utilities.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOCRunner implements AOC {
    private Map<Position, Integer> map = new HashMap<>();
    private Map<Position, Integer> map2 = new HashMap<>();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                String part1 = item.split(" -> ")[0];
                String part2 = item.split(" -> ")[1];
                Position p1 = parsePosition(part1);
                Position p2 = parsePosition(part2);
                if(Position.isLine(p1, p2)) {
                    for (Position position : Position.interval(p1, p2)) {
                        map.compute(position, (p, integer) -> (integer == null) ? 1 : integer + 1);
                    }
                }
                for (Position position : Position.interval(p1, p2)) {
                    map2.compute(position, (p, integer) -> (integer == null) ? 1 : integer + 1);
                }
            }
            Printer.println("Solution 1 : " + map.values().stream().filter(integer -> integer >= 2).count());
            Printer.println("Solution 2 : " + map2.values().stream().filter(integer -> integer >= 2).count());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Position parsePosition(String part1) {
        int x = Integer.parseInt(part1.split(",")[0].trim());
        int y = Integer.parseInt(part1.split(",")[1].trim());
        return new Position(x, y);
    }
}
