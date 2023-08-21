package aoc.exercises.year2022.day25;

import org.apache.commons.lang3.StringUtils;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/25">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final int BASE = 5;

    public record MinimalRest(long rest, int powerAmount) {
    }


    @Override
    public void run() {
        long number = 0;
        for (String line : inputList) {
            number += getDecimalValue(line);
        }
        solution1 = getPentalValue(number);
        solution2 = 0; // END OF THE YEAR !
    }

    private long getDecimalValue(String string) {
        List<Character> charList = toCharList(string);

        long result = 0;
        int maxPower = string.length() - 1;
        // 1=-0-2 = (1 * 5^5) + (-2 * 5^4) + (-1 * 5^3) + (0 * 5^2) + (-1 * 5^1) + (2 * 5^0)
        // 1*5^5 -> value*BASE^power
        for (int index = 0; index < string.length(); index++) {
            int value = getDecimalValue(charList.get(index));
            int power = maxPower - index;
            result += (long) (value * Math.pow(BASE, power));
        }
        return result;
    }

    private ArrayList<Character> toCharList(String string) {
        return new ArrayList<>(string.chars().mapToObj(c -> (char) c).toList());
    }

    private String getPentalValue(long number) {
        StringBuilder sb = new StringBuilder();

        int power = getStartPower(number);
        while (power >= 0) {
            MinimalRest minimalRest = getMinimalRest(number, power);
            number = minimalRest.rest;
            sb.append(getPentalValue(minimalRest.powerAmount));
            power--;
        }
        return StringUtils.stripStart(sb.toString(), "0");
    }

    /**
     * <pre>
     * Get the first value such that BASE * value is greater than number.
     * Example : if number = 4500, then return 6
     * because : 3125 (5^5) < 4500 < 15625 (5^6)
     * </pre>
     *
     * @param number : the number to use
     * @return the awaited value
     */
    private int getStartPower(long number) {
        int startPower = 0;
        while (Math.pow(BASE, startPower) < number) {
            startPower++;
        }
        return startPower;
    }

    private MinimalRest getMinimalRest(long number, int power) {
        long rest = Long.MAX_VALUE;
        int powerAmount = -2;
        for (int currentPowerAmount = -2; currentPowerAmount <= 2; currentPowerAmount++) {
            long currentRest = number - (long) (Math.pow(5, power) * currentPowerAmount);
            if (Math.abs(currentRest) > Math.abs(rest)) {
                break;
            }
            rest = currentRest;
            powerAmount = currentPowerAmount;
        }
        return new MinimalRest(rest, powerAmount);
    }

    private int getDecimalValue(Character character) {
        return switch (character) {
            case '0', '1', '2' -> Character.getNumericValue(character);
            case '=' -> -2;
            case '-' -> -1;
            default -> throw new IllegalArgumentException(character + " is not a valid value");
        };
    }

    private String getPentalValue(int value) {
        return switch (value) {
            case 0, 1, 2 -> String.valueOf(value);
            case -1 -> "-";
            case -2 -> "=";
            default -> throw new IllegalArgumentException(value + " is not a valid value");
        };
    }
}
