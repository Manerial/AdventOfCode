package aoc.exercises.year2016.day03;

import java.util.List;

public class PossibleShape {
    private final List<Integer> lengths;

    public PossibleShape(List<Integer> lengths) {
        this.lengths = lengths;
    }


    public boolean isValid() {
        int max = lengths.stream().mapToInt(Integer::intValue).max().orElse(0);
        int sumMin = lengths.stream().mapToInt(Integer::intValue).sum() - max;
        return max < sumMin;
    }
}
