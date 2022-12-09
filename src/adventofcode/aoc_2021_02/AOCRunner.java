package adventofcode.aoc_2021_02;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOCRunner implements AOC {
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String FORWARD = "forward";
    private static final String DEPTH = "depth";

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            Map<String, Integer> positions = new HashMap<>();
            resetPositions(positions);
            solution1(list, positions);
            Printer.println("Solution 1 : " + positions.get(FORWARD) * (positions.get(DOWN) - positions.get(UP)));

            resetPositions(positions);
            solution2(list, positions);
            Printer.println("Solution 2 : " + positions.get(FORWARD) * positions.get(DEPTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetPositions(Map<String, Integer> positions) {
        positions.put(FORWARD, 0);
        positions.put(UP, 0);
        positions.put(DOWN, 0);
        positions.put(DEPTH, 0);
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
            if(direction.equals(FORWARD)) {
                positions.put(DEPTH, positions.get(DEPTH) + getAim(positions) * movement);
            }
        }
    }

    private int getAim(Map<String, Integer> positions) {
        return positions.get(DOWN) - positions.get(UP);
    }
}
