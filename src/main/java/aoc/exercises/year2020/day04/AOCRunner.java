package aoc.exercises.year2020.day04;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Passport> passports = parseInput();
        solution1 = countValidPasswords(passports, false);
        solution2 = countValidPasswords(passports, true);
    }

    private List<Passport> parseInput() {
        List<Passport> passports = new ArrayList<>();
        List<String> reformattedInput = Arrays.stream(String.join(" ", inputList).split("\\s{2}")).toList();
        for (String passportStr : reformattedInput) {
            try {
                passports.add(new Passport(passportStr));
            } catch (Exception ignored) {
                // Nothing
            }
        }
        return passports;
    }

    private int countValidPasswords(List<Passport> passports, boolean strongCheck) {
        return passports.stream()
                .mapToInt(passport -> passport.isValid(strongCheck) ? 1 : 0)
                .sum();
    }
}