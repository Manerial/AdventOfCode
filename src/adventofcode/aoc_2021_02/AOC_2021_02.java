package adventofcode.aoc_2021_02;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import template.AOC;

public class AOC_2021_02 extends AOC {
    private static final String up = "up";
    private static final String down = "down";
    private static final String forward = "forward";
    private static final String depth = "depth";

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            Map<String, Integer> positions = new HashMap<>();
            resetPositions(positions);
            solution1(list, positions);
            Printer.println("Solution 1 : " + positions.get(forward) * (positions.get(down) - positions.get(up)));

            resetPositions(positions);
            solution2(list, positions);
            Printer.println("Solution 2 : " + positions.get(forward) * positions.get(depth));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetPositions(Map<String, Integer> positions) {
        positions.put(forward, 0);
        positions.put(up, 0);
        positions.put(down, 0);
        positions.put(depth, 0);
    }

    private void solution1(List<String> list, Map<String, Integer> positions) {
        for (String item : list) {
            String direction = item.split(" ")[0];
            int movement = Integer.parseInt(item.split(" ")[1]);
            positions.put(direction, movement + positions.get(direction));
        }
    }

    private void solution2(List<String> list, Map<String, Integer> positions) {
        for (String item : list) {
            String direction = item.split(" ")[0];
            int movement = Integer.parseInt(item.split(" ")[1]);
            positions.put(direction, movement + positions.get(direction));
            if(direction.equals(forward)) {
                positions.put(depth, positions.get(depth) + getAim(positions) * movement);
            }
        }
    }

    private int getAim(Map<String, Integer> positions) {
        return positions.get(down) - positions.get(up);
    }
}
