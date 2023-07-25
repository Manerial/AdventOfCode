package aoc.exercises.year2020.day05;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlanesSeat {
    private final int row;
    private final int column;

    public PlanesSeat(String planeId) {
        List<Character> rowChars = planeId.chars()
                .mapToObj(value -> (char) value)
                .limit(7)
                .toList();
        row = extracted(127, rowChars, 'F');

        List<Character> colChars = planeId.chars()
                .mapToObj(value -> (char) value)
                .skip(7)
                .toList();
        column = extracted(8, colChars, 'L');
    }

    private static int extracted(int initialValue, List<Character> planeId, char c) {
        AtomicInteger minRangeRow = new AtomicInteger(0);
        AtomicInteger maxRangeRow = new AtomicInteger(initialValue);
        planeId.forEach(value -> {
            int moy = (int) Math.ceil((double) (minRangeRow.get() + maxRangeRow.get()) / 2);
            if (value == c) {
                maxRangeRow.set(moy);
            } else {
                minRangeRow.set(moy);
            }
        });
        return minRangeRow.get();
    }

    public int getSeat() {
        return row * 8 + column;
    }
}
