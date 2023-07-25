package aoc.exercises.year2015.day05;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final String[] forbiddenValues = new String[]{"ab", "cd", "pq", "xy"};

    record RecordValidPassword(int solution1, int solution2) {
    }

    @Override
    public void run() {
        RecordValidPassword recordValidPassword = countValidPassword();
        solution1 = recordValidPassword.solution1;
        solution2 = recordValidPassword.solution2;
    }

    private RecordValidPassword countValidPassword() {
        int solution1 = 0;
        int solution2 = 0;
        for (String password : inputList) {
            if (!containsForbidden(password) && containsDoublon(password) && containsThreeVowels(password)) {
                solution1++;
            }
            if (containsTwoPairs(password) && containsDoublonWithAnyBetween(password)) {
                solution2++;
            }
        }
        return new RecordValidPassword(solution1, solution2);
    }

    /**
     * return true if the password contains forbidden values
     *
     * @param password : the password to check
     * @return true if the password contains forbidden values, false otherwise
     */
    private boolean containsForbidden(String password) {
        for (String forbidden : forbiddenValues) {
            if (password.contains(forbidden)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the password contains 3 vowels in a row
     * Example : BAEACAI = true, BACAIBA = false
     *
     * @param password : the password to check
     * @return true if the password contains 3 vowels in a row, false otherwise
     */
    private boolean containsThreeVowels(String password) {
        int countVowels = 0;
        for (char currentChar : password.toCharArray()) {
            if ("aeiou".contains(String.valueOf(currentChar))) {
                countVowels++;
            }
            if (countVowels >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the password contains 2 times the same characters in a row
     * Example : BAACXA = true, ABXBAC = false
     *
     * @param password : the password to check
     * @return true if the password contains 2 times the same characters in a row, false otherwise
     */
    private boolean containsDoublon(String password) {
        char[] passwordChars = password.toCharArray();

        for (int i = 0; i < passwordChars.length - 1; i++) {
            char currentChar = passwordChars[i];
            char nextChar = passwordChars[i + 1];
            if (currentChar == nextChar) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the password contains 2 times any pairs of characters
     * Example : ABXAB = true, ABBAA = false
     *
     * @param password : the password to check
     * @return true if the password contains 2 times any pairs of characters, false otherwise
     */
    private boolean containsTwoPairs(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            String subStr = password.substring(i, i + 2);
            String testPassword = password.replace(subStr, "");
            if (testPassword.length() <= password.length() - 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if the password contains 2 times the same characters with any character between them
     * Example : ABA = true, BAA = false
     *
     * @param password : the password to check
     * @return true if the password contains 2 times the same characters with any character between them, false otherwise
     */
    private boolean containsDoublonWithAnyBetween(String password) {
        char[] passwordChars = password.toCharArray();

        for (int i = 0; i < passwordChars.length - 2; i++) {
            char currentChar = passwordChars[i];
            char nextChar = passwordChars[i + 2];
            if (currentChar == nextChar) {
                return true;
            }
        }
        return false;
    }
}