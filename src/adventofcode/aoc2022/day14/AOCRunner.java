package adventofcode.aoc2022.day14;

import template.AOC;
import utilities.FileLoader;
import utilities.Position;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

/**
 * AdventOfCode 2022 day 14's instructions are <a href="https://adventofcode.com/2022/day/14">here</a>
 */
public class AOCRunner implements AOC {
    private final Cavern cavern = new Cavern();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            Position dropPosition = new Position(500, 0);
            addAir(list);
            addRocks(list);
            boolean canDrop = true;
            while (canDrop) {
                canDrop = cavern.dropSand(dropPosition);
            }
            Printer.println(cavern.countSand());

            cavern.fillBottomWithRocks();
            while (!cavern.isFull(dropPosition)) {
                cavern.dropSand(dropPosition);
            }
            Printer.println(cavern);
            Printer.println(cavern.countSand());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAir(List<String> list) {
        int maxY = 0;

        for (String item : list) {
            for (String coordinates : item.split(" -> ")) {
                int y = Integer.parseInt(coordinates.split(",")[1]);
                maxY = Integer.max(y, maxY);
            }
        }
        cavern.fill(0, 0, 1000, maxY + 2);
    }

    private void addRocks(List<String> list) {
        for (String item : list) {
            String[] mapOfCoordinates = item.split(" -> ");
            for (int index = 0; index < mapOfCoordinates.length - 1; index++) {
                String coordinates = mapOfCoordinates[index];
                String nextCoordinates = mapOfCoordinates[index + 1];

                Position position1 = new Position(coordinates, ",");
                Position position2 = new Position(nextCoordinates, ",");
                cavern.addRocks(position1, position2);
            }
        }
    }
}
