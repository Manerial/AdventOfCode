package aoc.exercises.year2024.day02;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        solution1 = inputList.stream()
                .filter(this::countSafeParts)
                .count();
        solution2 = inputList.stream()
                .filter(this::countSafePartsWithRemoval)
                .count();
    }

    private boolean countSafeParts(String line) {
        List<Long> parts = Arrays.stream(line.split(" "))
                .map(Long::parseLong)
                .toList();

        return checkList(parts);
    }

    private boolean countSafePartsWithRemoval(String line) {
        List<Long> parts = Arrays.stream(line.split(" "))
                .map(Long::parseLong)
                .toList();

        if (checkList(parts)) {
            return true;
        }
        for (int i = 0; i < parts.size(); i++) {
            List<Long> newParts = new ArrayList<>(parts);
            newParts.remove(i);
            if (checkList(newParts)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkList(List<Long> parts) {
        Character sign = null;
        for (int i = 0; i < parts.size() - 1; i++) {
            // check if the next number is distant of the current one within the specified range (1-3)
            long valAtIndex = parts.get(i);
            long valAtNextIndex = parts.get(i + 1);
            long diff = Math.abs(valAtIndex - valAtNextIndex);
            if (diff < 1 || diff > 3) {
                return false;
            }
            // check sign of the current number against the next one
            char currentSign = valAtIndex > valAtNextIndex ? '-' : '+';
            if (sign == null) {
                sign = currentSign;
            } else if (sign != currentSign) {
                return false;
            }
        }
        return true;
    }
}