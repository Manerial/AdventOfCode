package aoc.exercises.year2020.day02;

public class PasswordChecker {
    private final int value1;
    private final int value2;
    private final char charToCheck;
    private final String password;

    public PasswordChecker(String input) {
        //input = "X-Y C: PASSWORD"
        String[] splitLine = input.split("[\\s:-]+");
        //splitLine = ["X", "Y", "C", "PASSWORD"]

        value1 = Integer.parseInt(splitLine[0]);
        value2 = Integer.parseInt(splitLine[1]);
        charToCheck = splitLine[2].charAt(0); // Parse string to char
        password = splitLine[3];
    }

    public boolean isValidByCountChar() {
        long countChars = password
                .chars()
                .filter(value -> value == charToCheck)
                .count();
        return countChars >= value1 && countChars <= value2;
    }

    public boolean isValidByPresence() {
        char charPos1 = password.charAt(value1 - 1);
        char charPos2 = password.charAt(value2 - 1);
        return charPos1 == charToCheck ^ charPos2 == charToCheck;
    }
}
