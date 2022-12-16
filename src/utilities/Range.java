package utilities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Range implements Comparable<Range> {
    private Integer borneMin;
    private Integer borneMax;

    public Range(Integer a, Integer b) {
        borneMin = (a > b) ? b : a;
        borneMax = (a > b) ? a : b;
    }

    public boolean isOverlappedBy(Range range2) {
        return (this.borneMin >= range2.borneMin && this.borneMin <= range2.borneMax) ||
                (this.borneMax >= range2.borneMin && this.borneMax <= range2.borneMax);
    }

    public boolean contains(Range range2) {
        return this.borneMin <= range2.borneMin && this.borneMax >= range2.borneMax;
    }

    public void merge(Range range) {
        this.borneMin = Integer.min(borneMin, range.borneMin);
        this.borneMax = Integer.max(borneMax, range.borneMax);
    }

    public int size() {
        return borneMax - borneMin + 1;
    }

    public static List<Integer> toList(int a, int b) {
        List<Integer> list = new ArrayList<>();
        if (a < b) {
            for (int i = a; i <= b; i++) {
                list.add(i);
            }
        } else if (a > b) {
            for (int i = a; i >= b; i--) {
                list.add(i);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "[" + borneMin + ".." + borneMax + "]";
    }

    public boolean canBeMerge(Range range) {
        return isOverlappedBy(range) || range.isOverlappedBy(this) || contains(range) || range.contains(this) || borneMin == range.borneMax + 1 || range.borneMin == borneMax + 1;
    }

    @Override
    public int compareTo(Range o) {
        return borneMin - o.borneMin;
    }
}
