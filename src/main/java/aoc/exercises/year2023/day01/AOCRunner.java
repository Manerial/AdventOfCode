package aoc.exercises.year2023.day01;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    private String[] alphaDigits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public void run() {
        solution1 = inputList.stream()
                .mapToInt(this::extractNumber)
                .sum();
        solution2 = inputList.stream()
                .mapToInt(this::extractNumericNumber)
                .sum();
    }

    private int extractNumber(String s) {
        StringBuilder digitsBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                digitsBuilder.append(c);
            }
        }
        String digits = digitsBuilder.toString();
        char firstChar = digits.charAt(0);
        char lastChar = digits.charAt(digits.length() - 1);
        return Integer.parseInt(firstChar + "" + lastChar);
    }

    private int extractNumericNumber(String s) {
        int minIndexFirstDigit = s.length();
        int maxIndexFirstDigit = 0;

        String firstChar = "";
        String lastChar = "";
        for (String digit : alphaDigits) {
            int index = s.indexOf(digit);
            if (index == -1) {
                continue;
            }
            if (index <= minIndexFirstDigit) {
                firstChar = digit;
                minIndexFirstDigit = index;
            }
            int lastIndex = s.lastIndexOf(digit);
            if (lastIndex == -1) {
                continue;
            }
            if (lastIndex >= maxIndexFirstDigit) {
                lastChar = digit;
                maxIndexFirstDigit = lastIndex;
            }
        }

        firstChar = cleanValue(firstChar);
        lastChar = cleanValue(lastChar);

        return Integer.parseInt(firstChar + lastChar);
    }

    private String cleanValue(String firstChar) {
        return firstChar.replace("one", "1")
                .replace("two", "2")
                .replace("three", "3")
                .replace("four", "4")
                .replace("five", "5")
                .replace("six", "6")
                .replace("seven", "7")
                .replace("eight", "8")
                .replace("nine", "9");
    }
}