package aoc.exercises.year2022.day07;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static utilities.ResourceIO.DELIMITER;

@Data
public class Directory {
    private static final String RN = DELIMITER;
    private String name;
    private List<Directory> directories;
    private List<File> files;

    public Directory(String name) {
        this.name = name;
        directories = new ArrayList<>();
        files = new ArrayList<>();
    }

    public int getTotalSize() {
        int size = files.stream().map(File::getSize).reduce(0, Integer::sum);
        size += directories.stream()
                .map(Directory::getTotalSize)
                .reduce(0, Integer::sum);
        return size;
    }
}
