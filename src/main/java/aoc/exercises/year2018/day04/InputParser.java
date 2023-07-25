package aoc.exercises.year2018.day04;

import utilities.AbstractInputParser;
import utilities.errors.NotAcceptedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<List<Guard>> {
    private static final Pattern GUARD_ID_PATTERN = Pattern.compile("]\\D*(\\d+)");
    private static final Pattern MINUTE_PATTERN = Pattern.compile(":(\\d+)");

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Guard> parseInput() {
        inputList = sortInput();
        List<Guard> guards = new ArrayList<>();
        Guard currentGuard = null;
        String sleepingLine = null;
        for (String line : inputList) {
            if (isBeginShift(line)) {
                currentGuard = getOrCreateGuard(guards, line);
            } else if (isFallsAsleep(line)) {
                sleepingLine = line;
            } else {
                extractGuardData(currentGuard, sleepingLine, line);
            }
        }
        return guards;
    }

    private List<String> sortInput() {
        return inputList.stream()
                .sorted(String::compareTo)
                .toList();
    }

    private Guard getOrCreateGuard(List<Guard> guards, String line) {
        int currentGuardId = extractDataWithPattern(line, GUARD_ID_PATTERN);
        Guard currentGuard = guards.stream()
                .filter(guard1 -> guard1.getGuardId() == currentGuardId)
                .findFirst()
                .orElse(new Guard(currentGuardId));
        if (!guards.contains(currentGuard)) {
            guards.add(currentGuard);
        }
        return currentGuard;
    }

    private boolean isBeginShift(String line) {
        return line.contains("begins shift");
    }

    private boolean isFallsAsleep(String line) {
        return line.contains("falls asleep");
    }

    private void extractGuardData(Guard guard, String sleepingLine, String wakeUpLine) {
        if (guard == null) {
            throw new NullPointerException("First guard is null, please check the input / code !");
        }
        int timeSleep = extractDataWithPattern(sleepingLine, MINUTE_PATTERN);
        int timeWakeUp = extractDataWithPattern(wakeUpLine, MINUTE_PATTERN);
        for (int minute = timeSleep; minute < timeWakeUp; minute++) {
            guard.incrementSleep(minute);
        }
    }

    private int extractDataWithPattern(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new NotAcceptedValue(line);
    }
}
