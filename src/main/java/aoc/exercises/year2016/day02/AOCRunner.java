package aoc.exercises.year2016.day02;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<String> directionsList = inputList;

        Keypad keypad = new Keypad('5');
        keypad.addKeyRow("123");
        keypad.addKeyRow("456");
        keypad.addKeyRow("789");
        solution1 = getCode(directionsList, keypad);

        keypad = new Keypad('5');
        keypad.addKeyRow("  1  ");
        keypad.addKeyRow(" 234 ");
        keypad.addKeyRow("56789");
        keypad.addKeyRow(" ABC ");
        keypad.addKeyRow("  D  ");
        solution2 = getCode(directionsList, keypad);
    }

    private String getCode(List<String> directionsList, Keypad keypad) {
        StringBuilder code = new StringBuilder();
        for (String directions : directionsList) {
            directions.chars()
                    .forEach(direction -> keypad.move((char) direction));
            code.append(keypad.getCurrentKey());
        }
        return code.toString();
    }
}