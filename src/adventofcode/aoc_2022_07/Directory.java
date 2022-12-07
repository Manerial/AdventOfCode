package adventofcode.aoc_2022_07;

import lombok.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Data
public class Directory {
    private static final String RN = "\r\n";
    private static final String T = "\t";

    private String name;
    private Map<String, Object> directories;

    public Directory(String name) {
        this.name = name;
        directories = new HashMap<>();
    }

    public int getTotalSize() {
        int size = 0;
        Iterator<String> iter = directories.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            Object object = directories.get(name);
            if(object instanceof Directory) {
                size += ((Directory) object).getTotalSize();
            } else {
                size += (int) object;
            }
        }
        return size;
    }

    @Override
    public String toString() {
        String str = name + RN;
        Iterator<String> iter = directories.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            Object object = directories.get(name);
            str += object.toString().replaceAll("(?m)^", T) + RN;
        }
        return str;
    }
}
