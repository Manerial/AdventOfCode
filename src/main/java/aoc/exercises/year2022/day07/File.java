package aoc.exercises.year2022.day07;

import lombok.Data;

@Data
public class File {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return name + " : " + size;
    }
}
