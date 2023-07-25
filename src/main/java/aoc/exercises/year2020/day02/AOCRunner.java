package aoc.exercises.year2020.day02;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<PasswordChecker> passwordCheckers = inputList.stream()
                .map(PasswordChecker::new)
                .toList();
        solution1 = passwordCheckers.stream()
                .mapToInt(passwordChecker -> passwordChecker.isValidByCountChar() ? 1 : 0)
                .sum();
        solution2 = passwordCheckers.stream()
                .mapToInt(passwordChecker -> passwordChecker.isValidByPresence() ? 1 : 0)
                .sum();
    }
}