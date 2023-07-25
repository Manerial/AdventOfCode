package aoc.exercises.year2016.day02;

import utilities.errors.NotAcceptedValue;

import java.util.ArrayList;
import java.util.List;

public class Keypad {
    private static final char FORBIDDEN_KEY = ' ';
    private final List<List<Character>> keyRows;
    private char currentKey;

    public Keypad(char startingKey) {
        keyRows = new ArrayList<>();
        currentKey = startingKey;
    }

    public void addKeyRow(String keyRowStr) {
        keyRows.add(keyRowStr.chars()
                .mapToObj(c -> (char) c)
                .toList());
    }

    public char getCurrentKey() {
        return currentKey;
    }

    public void move(char direction) {
        List<Character> currentRow = keyRows.stream()
                .filter(row -> row.contains(currentKey))
                .findFirst()
                .orElseThrow(() -> new NotAcceptedValue(currentKey));
        int rowIndex = keyRows.indexOf(currentRow);
        int columnIndex = currentRow.indexOf(currentKey);
        switch (direction) {
            case 'U' -> rowIndex -= 1;
            case 'D' -> rowIndex += 1;
            case 'L' -> columnIndex -= 1;
            case 'R' -> columnIndex += 1;
            default -> throw new NotAcceptedValue(direction);
        }
        moveToNewKey(rowIndex, columnIndex);
    }

    private void moveToNewKey(int rowIndex, int columnIndex) {
        if (!isRowIndexInBound(rowIndex) || !isColumnIndexInBound(columnIndex)) {
            return;
        }
        char newKey = keyRows.get(rowIndex).get(columnIndex);
        if (newKey != FORBIDDEN_KEY) {
            currentKey = newKey;
        }
    }

    private boolean isRowIndexInBound(int rowIndex) {
        return rowIndex >= 0 && rowIndex < keyRows.size();
    }

    private boolean isColumnIndexInBound(int columnIndex) {
        return columnIndex >= 0 && columnIndex < keyRows.get(0).size();
    }
}
