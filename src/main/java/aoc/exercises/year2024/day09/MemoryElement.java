package aoc.exercises.year2024.day09;

import lombok.Data;

@Data
public class MemoryElement {
    private final int id;
    private final int startFileIndex;

    public long getMinId() {
        return id > 0 ? id : 0L;
    }
}
