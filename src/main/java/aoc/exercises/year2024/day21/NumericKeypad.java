package aoc.exercises.year2024.day21;

import aoc.common_objects.Position;

public class NumericKeypad extends Keypad {

    public NumericKeypad() {
        super();
        keymap.put('0', new Position(1, 3));
        keymap.put('1', new Position(0, 2));
        keymap.put('2', new Position(1, 2));
        keymap.put('3', new Position(2, 2));
        keymap.put('4', new Position(0, 1));
        keymap.put('5', new Position(1, 1));
        keymap.put('6', new Position(2, 1));
        keymap.put('7', new Position(0, 0));
        keymap.put('8', new Position(1, 0));
        keymap.put('9', new Position(2, 0));
        keymap.put('A', new Position(2, 3));
        forbidenPosition = new Position(0, 3);
    }

    /**
     * Count the total number of moves made to complete the specified path.
     *
     * @param currentPath : the path to follow
     * @return the total number of moves made
     */
    public long countTotalMoves(String currentPath) {
        long result = 0;
        // Always start on the A key
        String pathWithStart = "A" + currentPath;
        // Split the path into pairs of keys : for "012A", we do A0, 01, 12, 2A
        for (int i = 0; i < pathWithStart.length() - 1; i++) {
            result += movesByKeyPair.get(pathWithStart.substring(i, i + 2));
        }
        return result;
    }
}
