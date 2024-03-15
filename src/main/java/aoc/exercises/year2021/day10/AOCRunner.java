package aoc.exercises.year2021.day10;

import utilities.AbstractAOC;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final List<Character> illegalCharacters = new ArrayList<>();
    private final List<Deque<Character>> incompleteLine = new ArrayList<>();

    @Override
    public void run() {
        inputList.forEach(this::treatLine);
        solution1 = illegalCharacters.stream()
                .mapToInt(character -> this.getMatchingValue(character, true))
                .sum();
        List<Long> incompleteScores = incompleteLine.stream()
                .map(this::getIncompletedLineValue)
                .sorted()
                .toList();
        solution2 = incompleteScores.get(incompleteScores.size() / 2);
    }

    /**
     * Extract the corrupted character of the line and the missing characters if they exist.
     * Any opening character - ([{< - must be closed by its matching one - )]}> -
     * If it is another one, then the line is corrupted
     * If there is no more characters, the line is incomplete
     *
     * @param line : the line to treat
     */
    private void treatLine(String line) {
        Deque<Character> awaitedCharacters = new ArrayDeque<>();
        for (char character : line.toCharArray()) {
            switch (character) {
                case '{', '[', '(', '<' -> awaitedCharacters.addFirst(getMatchingPair(character));
                case '}', ']', ')', '>' -> {
                    if (awaitedCharacters.getFirst() != character) {
                        illegalCharacters.add(character);
                        return;
                    } else {
                        awaitedCharacters.removeFirst();
                    }
                }
                default -> throw getCharacterException(character);
            }
        }
        if (!awaitedCharacters.isEmpty()) {
            incompleteLine.add(awaitedCharacters);
        }
    }

    private long getIncompletedLineValue(Deque<Character> characters) {
        long finalValue = 0;
        while (!characters.isEmpty()) {
            finalValue *= 5;
            finalValue += getMatchingValue(characters.removeFirst(), false);
        }
        return finalValue;
    }

    private Character getMatchingPair(char character) {
        switch (character) {
            case '(' -> {
                return ')';
            }
            case '[' -> {
                return ']';
            }
            case '{' -> {
                return '}';
            }
            case '<' -> {
                return '>';
            }
            default -> throw getCharacterException(character);
        }
    }

    private int getMatchingValue(Character character, boolean isCorrupted) {
        switch (character) {
            case ')' -> {
                return isCorrupted ? 3 : 1;
            }
            case ']' -> {
                return isCorrupted ? 57 : 2;
            }
            case '}' -> {
                return isCorrupted ? 1197 : 3;
            }
            case '>' -> {
                return isCorrupted ? 25137 : 4;
            }
            default -> throw getCharacterException(character);
        }
    }

    private RuntimeException getCharacterException(Character character) {
        return new IllegalArgumentException("Illegal character: " + character);
    }
}