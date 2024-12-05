package aoc.exercises.year2024.day05;

import lombok.Data;

@Data
public class PageOrderRule {
    private int pageBefore;
    private int pageAfter;

    public PageOrderRule(String line) {
        String[] parts = line.split("\\|");
        pageBefore = Integer.parseInt(parts[0]);
        pageAfter = Integer.parseInt(parts[1]);
    }
}
