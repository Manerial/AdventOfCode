package aoc.exercises.year2021.day08;

import java.util.*;

public class DigitSignal {
    private final List<String> signalPatterns;
    private final List<String> outputSignals;
    private final Map<Integer, String> mapOutput = new HashMap<>();

    public DigitSignal(List<String> signalPatterns, List<String> outputSignals) {
        this.signalPatterns = signalPatterns;
        this.outputSignals = outputSignals;

        this.mapOutput.put(1, signalPatterns.stream().filter(s -> s.length() == 2).findFirst().orElseThrow());
        this.mapOutput.put(4, signalPatterns.stream().filter(s -> s.length() == 4).findFirst().orElseThrow());
        this.mapOutput.put(7, signalPatterns.stream().filter(s -> s.length() == 3).findFirst().orElseThrow());
        this.mapOutput.put(8, signalPatterns.stream().filter(s -> s.length() == 7).findFirst().orElseThrow());

        this.mapOutput.put(9, getStringThatMatches(6, 4, 7));
        this.mapOutput.put(0, getStringThatMatches(6, -4, 7));
        this.mapOutput.put(6, getStringThatMatches(6, -1, -9));
        this.mapOutput.put(2, getStringThatMatches(5, -9));
        this.mapOutput.put(5, getStringThatMatches(5, 6));
        this.mapOutput.put(3, getStringThatMatches(5, 7, 9));
    }

    private String getStringThatMatches(int length, int... intToMatch) {
        return signalPatterns.stream()
                .filter(s -> s.length() == length)
                .filter(s -> {
                    boolean matches = true;
                    for (int i : intToMatch) {
                        if (i > 0) {
                            matches &= containsAllBothWays(s, this.mapOutput.get(i));
                        } else {
                            matches &= !containsAllBothWays(s, this.mapOutput.get(-i));
                        }
                    }
                    return matches;
                })
                .findFirst()
                .orElseThrow();
    }

    private boolean containsAllBothWays(String string1, String string2) {
        Set<String> chars1 = new HashSet<>(Arrays.stream(string1.split("")).toList());
        Set<String> chars2 = new HashSet<>(Arrays.stream(string2.split("")).toList());
        return chars2.containsAll(chars1) || chars1.containsAll(chars2);
    }

    private boolean containsAllStrict(String string1, String string2) {
        Set<String> chars1 = new HashSet<>(Arrays.stream(string1.split("")).toList());
        Set<String> chars2 = new HashSet<>(Arrays.stream(string2.split("")).toList());
        return chars2.containsAll(chars1) && chars1.containsAll(chars2);
    }

    public long countEasyOutput() {
        return outputSignals.stream()
                .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
                .count();
    }

    public int getOutput() {
        String outputStr = outputSignals.stream()
                .map(this::parseOutput)
                .reduce("", (a, b) -> a + b);
        return Integer.parseInt(outputStr);
    }

    private String parseOutput(String s) {
        for (Map.Entry<Integer, String> entry : this.mapOutput.entrySet()) {
            if (containsAllStrict(s, entry.getValue())) {
                return entry.getKey().toString();
            }
        }
        throw new IllegalArgumentException();
    }
}
