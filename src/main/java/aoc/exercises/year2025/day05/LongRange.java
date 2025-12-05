package aoc.exercises.year2025.day05;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LongRange {
    private long min;
    private long max;

    public static LongRange toRange(String string, String splitter) {
        String[] splits = string.split(splitter);
        return new LongRange(Long.parseLong(splits[0]), Long.parseLong(splits[1]));
    }

    public boolean contains(long number) {
        return number >= min && number <= max;
    }

    public long interval() {
        return max - min + 1;
    }

    public void reduceIntervalTo0() {
        min = 0;
        max = -1;
    }
}