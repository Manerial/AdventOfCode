package aoc.exercises.year2024.day21;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/21">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final List<DirectionalKeypad> directionalKeypads = new ArrayList<>();

    @Override
    public void run() {
        initKeyboards();

        solution1 = inputList.stream().mapToLong(line -> getComplexity(line, 2)).sum();
        solution2 = inputList.stream().mapToLong(line -> getComplexity(line, 25)).sum();
    }

    private void initKeyboards() {
        DirectionalKeypad previousKeypad = null;
        for (int i = 0; i < 25; i++) {
            DirectionalKeypad newDirectionalKeypad = new DirectionalKeypad();
            newDirectionalKeypad.setMovesByKeyPair(previousKeypad);
            directionalKeypads.add(newDirectionalKeypad);
            previousKeypad = newDirectionalKeypad;
        }
    }

    private long getComplexity(String line, int keyboards) {
        NumericKeypad numericKeypad = new NumericKeypad();
        numericKeypad.setMovesByKeyPair(directionalKeypads.get(keyboards - 1));

        return numericKeypad.countTotalMoves(line) * getNumericPart(line);
    }

    /**
     * Get numeric part from the line.
     * 140A -> 140
     *
     * @param line the line to extract the numeric part.
     * @return the numeric part from the line.
     */
    private int getNumericPart(String line) {
        return Integer.parseInt(line.replaceAll("\\D", ""));
    }
}