package aoc.exercises.year2018.day04;

import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Guard {
    @Getter
    private int guardId;

    private final Map<Integer, Integer> nbOccurrencesByMinute = new HashMap<>();

    public Guard(int guardId) {
        this.guardId = guardId;
    }

    public int compareSleep(Guard guard) {
        return this.getFullSleepTime() - guard.getFullSleepTime();
    }

    public void incrementSleep(int minute) {
        int newSleep = nbOccurrencesByMinute.getOrDefault(minute, 0) + 1;
        nbOccurrencesByMinute.put(minute, newSleep);
    }

    public int getFullSleepTime() {
        return nbOccurrencesByMinute.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getMostSleptMinute() {
        return nbOccurrencesByMinute.entrySet().stream() // Entry Minute / occurrences
                .max(Comparator.comparingInt(Map.Entry::getValue)) // max occurrences (if many, take first)
                .map(Map.Entry::getKey) // matching minute
                .orElse(-1);
    }

    public int getBiggestOccurrence() {
        return nbOccurrencesByMinute.values().stream() // Stream of frequencies
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1);
    }

    public Optional<Integer> getMinuteByOccurrence(int occurrence) {
        if(nbOccurrencesByMinute.containsValue(occurrence)) {
            return nbOccurrencesByMinute.entrySet().stream()
                    .filter(integerIntegerEntry -> integerIntegerEntry.getValue() == occurrence)
                    .findFirst()
                    .map(Map.Entry::getKey);
        }
        return Optional.empty();
    }
}
