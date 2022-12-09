package utilities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Directory {
    private static final String RN = "\r\n";
    private static final String T = "\t";

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

    @Override
    public String toString() {
        String str = name + RN;
        str += files.stream().map(File::toString).reduce(String::join);
        str += directories.stream()
                .map(Directory::toString)
                .reduce("", ((s, s2) ->
                        s + s2.replaceAll("(?m)^", "|___")
                ));
        return str;
    }
}
