package aoc.exercises.year2023.day03;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Map<Position, Character> grid;
    int lines;
    int chars;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        grid = inputParser.parseInput();
        lines = inputList.size();
        chars = inputList.get(0).length();

        solution1 = sumPartsNumber();
        solution2 = sumGearRatio();
    }

    private int sumPartsNumber() {
        int sum = 0;
        int currentValue = 0;
        for (int lineIndex = 0; lineIndex < lines; lineIndex++) {
            for (int charIndex = 0; charIndex <= chars; charIndex++) {
                Position position = new Position(lineIndex, charIndex);
                char charAtPos = grid.getOrDefault(position, '.');
                if (Character.isDigit(charAtPos)) {
                    currentValue *= 10;
                    currentValue += Character.getNumericValue(charAtPos);
                } else if (currentValue > 0) {
                    if (hasSymbol(position, currentValue)) {
                        sum += currentValue;
                    }
                    currentValue = 0;
                }
            }
        }
        return sum;
    }

    private boolean hasSymbol(Position position, int currentValue) {
        int size = String.valueOf(currentValue).length() + 1;
        int line = position.getX();
        int column = position.getY();
        for (int lineIndex = line - 1; lineIndex <= line + 1; lineIndex++) {
            for (int charIndex = column - size; charIndex <= column; charIndex++) {
                if (isSymbol(new Position(lineIndex, charIndex))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSymbol(Position position) {
        char charAtPosition = grid.getOrDefault(position, '.');
        boolean isDigit = Character.isDigit(charAtPosition);
        boolean isPoint = charAtPosition == '.';
        return !isDigit && !isPoint;
    }

    private int sumGearRatio() {
        int sum = 0;
        for (int lineIndex = 0; lineIndex < lines; lineIndex++) {
            for (int charIndex = 0; charIndex < chars; charIndex++) {
                Position position = new Position(lineIndex, charIndex);
                char charAtPos = grid.get(position);
                if (charAtPos == '*') {
                    sum += getGearRatio(position);
                }
            }
        }
        return sum;
    }

    private int getGearRatio(Position position) {
        List<Integer> aroundNumbers = new ArrayList<>();
        int number = 0;
        int line = position.getX();
        int column = position.getY();
        for (int lineIndex = line - 1; lineIndex <= line + 1; lineIndex++) {
            for (int charIndex = column - 1; charIndex <= column + 1; charIndex++) {
                Position currentPosition = new Position(lineIndex, charIndex);
                boolean isDigit = isDigit(currentPosition);
                if (isDigit && number == 0) {
                    number = getWholeNumber(currentPosition);
                    aroundNumbers.add(number);
                } else if (!isDigit) {
                    number = 0;
                }
            }
            number = 0;
        }

        if (aroundNumbers.size() == 2) {
            return aroundNumbers.get(0) * aroundNumbers.get(1);
        }
        return 0;
    }

    private Integer getWholeNumber(Position position) {
        while (isDigit(position)) {
            position.decY();
        }
        position.incY();

        StringBuilder digitString = new StringBuilder();
        while (isDigit(position)) {
            digitString.append(grid.get(position));
            position.incY();
        }

        return Integer.parseInt(digitString.toString());
    }

    private boolean isDigit(Position position) {
        return Character.isDigit(grid.getOrDefault(position, '.'));
    }
}