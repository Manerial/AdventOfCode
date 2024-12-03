package aoc.exercises.year2015.day11;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String currentPassword = inputList.get(0);
        solution1 = getNextPassword(currentPassword);
        solution2 = getNextPassword((String) solution1);
    }

    private String getNextPassword(String currentPassword) {
        char[] chars = currentPassword.toCharArray();
        increment(chars, chars.length - 1);

        String nextPassword = new String(chars);
        while (!isValid(nextPassword)) {
            increment(chars, chars.length - 1);
            nextPassword = new String(chars);
        }
        return nextPassword;
    }

    private boolean isValid(String password) {
        return !containsIOL(password) && passConsecutiveCount(password) && hasTwoDistinctPairs(password);
    }

    private static boolean containsIOL(String password) {
        return password.contains("i") || password.contains("o") || password.contains("l");
    }

    private boolean passConsecutiveCount(String password) {
        for (int i = 1; i < password.length() - 2; i++) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            char c3 = password.charAt(i + 2);
            if (c1 == c2 - 1 && c2 == c3 - 1) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTwoDistinctPairs(String password) {
        List<Character> pairs = new ArrayList<>();
        for (int i = 0; i < password.length() - 1; i++) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            if (c1 == c2 && !pairs.contains(c1)) {
                pairs.add(c1);
            }
        }
        return pairs.size() >= 2;
    }

    private void increment(char[] chars, int index) {
        chars[index]++;
        if (chars[index] > 'z') {
            chars[index] = 'a';
            if (index > 0) {
                increment(chars, index - 1);
            }
        }
    }
}