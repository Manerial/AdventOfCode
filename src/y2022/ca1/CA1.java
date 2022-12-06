package y2022.ca1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utilities.FileLoader;

public class CA1 {
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<Integer> treeList = new ArrayList<>();
            Integer currentCarry = 0;
            for (String item : list) {
                try {
                    Integer carry = Integer.parseInt(item);
                    currentCarry += carry;
                } catch (NumberFormatException e) {
                    treeList.add(currentCarry);
                    currentCarry = 0;
                }
            }
            Collections.sort(treeList);
            // Solution 1 : Le plus
            System.out.println("Solution 1 : " + treeList.get(treeList.size() - 1));

            // Solution 2 : Top 3
            System.out.println("Solution 2 : " + (
                    treeList.get(treeList.size() - 1)
                            + treeList.get(treeList.size() - 2)
                            + treeList.get(treeList.size() - 3)
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
