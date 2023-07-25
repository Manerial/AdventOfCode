package aoc.exercises.year2019.day04;

import utilities.AbstractAOC;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    record RecordValidPassword(int solution1, int solution2) {
    }

    @Override
    public void run() {
        String[] split = inputList.get(0).split("-");
        int min = Integer.parseInt(split[0]);
        int max = Integer.parseInt(split[1]);
        min = getNext(min - 1); // - 1 avoids ignoring first

        RecordValidPassword recordValidPassword = countValidPasswords(min, max);
        solution1 = recordValidPassword.solution1;
        solution2 = recordValidPassword.solution2;
    }

    private RecordValidPassword countValidPasswords(int min, int max) {
        int countValidPasswordSolution1 = 0;
        int countValidPasswordSolution2 = 0;
        // Way to improve the increase
        for (int passwordIncr = min; passwordIncr <= max; passwordIncr = getNext(passwordIncr)) {
            String password = String.valueOf(passwordIncr);
            countValidPasswordSolution1 += containsDoublon(password, false) ? 1 : 0;
            countValidPasswordSolution2 += containsDoublon(password, true) ? 1 : 0;
        }
        return new RecordValidPassword(countValidPasswordSolution1, countValidPasswordSolution2);
    }

    private int getNext(int passwordIncr) {
        // Example with passwordIncr = 349999
        char[] passwordChars = String.valueOf(passwordIncr + 1).toCharArray();
        // passwordChars = ['3', '5', '0', '0', '0', '0']
        for (int charIndex = 0; charIndex < passwordChars.length - 1; charIndex++) {
            char currentChar = passwordChars[charIndex];
            char nextChar = passwordChars[charIndex + 1];
            if (currentChar > nextChar) {
                setNextCharsToCurrent(passwordChars, charIndex + 1, currentChar);
                // passwordChars = ['3', '5', '5', '5', '5', '5']
                return charArrayToInt(passwordChars);
            }
        }
        return charArrayToInt(passwordChars);
    }

    private static void setNextCharsToCurrent(char[] passwordChars, int currentIndex, char currentChar) {
        for (int charIndex = currentIndex; charIndex < passwordChars.length; charIndex++) {
            passwordChars[charIndex] = currentChar;
        }
    }

    private static int charArrayToInt(char[] passwordChars) {
        return Integer.parseInt(new String(passwordChars));
    }

    private boolean containsDoublon(String password, boolean checkTrueDoublon) {
        List<Character> passwordChars = password.chars()
                .mapToObj(value -> (char) value)
                .toList();

        for (char c : passwordChars) {
            int frequency = Collections.frequency(passwordChars, c);
            // frequency == 2 is always true
            // Else it is a real doublon (not triple). Triple is non-compliant with ex2
            if (frequency == 2 || frequency > 2 && !checkTrueDoublon) {
                return true;
            }
        }
        return false;
    }
}