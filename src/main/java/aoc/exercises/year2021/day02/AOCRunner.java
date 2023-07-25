package aoc.exercises.year2021.day02;

import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String FORWARD = "forward";
    private static final String DEPTH = "depth";

    @Override
    public void run() {
        Map<String, Integer> positions = new HashMap<>();
        resetPositions(positions);
        solution1(inputList, positions);
        solution1 = positions.get(FORWARD) * (positions.get(DOWN) - positions.get(UP));

        resetPositions(positions);
        solution2(inputList, positions);
        solution2 = positions.get(FORWARD) * positions.get(DEPTH);
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
            if (direction.equals(FORWARD)) {
                positions.put(DEPTH, positions.get(DEPTH) + getAim(positions) * movement);
            }
        }
    }

    private int getAim(Map<String, Integer> positions) {
        return positions.get(DOWN) - positions.get(UP);
    }
}
