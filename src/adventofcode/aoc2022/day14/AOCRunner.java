package adventofcode.aoc2022.day14;

import template.AOC;
import utilities.FileLoader;
import utilities.Position;
import utilities.Printer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * AdventOfCode 2022 day 14's instructions are <a href="https://adventofcode.com/2022/day/14">here</a>
 */
public class AOCRunner implements AOC {

    @Override
    public void run(String file) throws IOException {
        List<String> list = FileLoader.readListFromFile(file);

        Cavern cavern = new Cavern(getDepth(list));
        cavern.setDropPosition(new Position(500, 0));
        cavern.fillWithAir();

        addRocks(cavern, list);

        cavern.dropSandUntilAbyss();
        Printer.println("Solution 1 : " + cavern.countSand());

        cavern.fillBottomWithRocks();
        cavern.dropSandUntilFull();
        Printer.println("Solution 2 : " + cavern.countSand());
    }

    private int getDepth(List<String> list) {
        return list.stream().flatMap(
                item -> Stream.of(item.split(" -> "))
                        .map(coordinates -> coordinates.split(",")[1])
                        .map(Integer::parseInt)
        ).reduce(Integer::max).orElse(0);
    }

    private void addRocks(Cavern cavern, List<String> list) {
        for (String walls : list) {
            String[] wallCoordinates = walls.split(" -> ");
            for (int index = 0; index < wallCoordinates.length - 1; index++) {
                Position beginWall = new Position(wallCoordinates[index], ",");
                Position endWall = new Position(wallCoordinates[index + 1], ",");
                cavern.addRocks(beginWall, endWall);
            }
        }
    }
}
