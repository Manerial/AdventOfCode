package adventofcode.y2022.ca1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class CA1 {
    private static Integer currentCarry = 0;
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<Integer> treeList = new ArrayList<>();
            for (String item : list) {
                getCurrentCarry(treeList, item);
            }
            Collections.sort(treeList);
            // Solution 1 : Le plus
            Printer.println("Solution 1 : " + treeList.get(treeList.size() - 1));

            // Solution 2 : Top 3
            Printer.println("Solution 2 : " + (
                    treeList.get(treeList.size() - 1)
                            + treeList.get(treeList.size() - 2)
                            + treeList.get(treeList.size() - 3)
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getCurrentCarry(List<Integer> treeList, String item) {
        try {
            int carry = Integer.parseInt(item);
            currentCarry += carry;
        } catch (NumberFormatException e) {
            treeList.add(currentCarry);
            currentCarry = 0;
        }
    }
}
