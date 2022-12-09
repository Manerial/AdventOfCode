package utilities;

import lombok.Data;

@Data
public class File {
    private static final String RN = "\r\n";
    private static final String T = "\t";

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
