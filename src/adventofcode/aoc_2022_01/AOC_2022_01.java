package adventofcode.aoc_2022_01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

public class AOC_2022_01 extends AOC {
    private Integer currentCarry = 0;

    @Override
    public void run(String file) {
        try {
			// Read inputs
            List<String> list = FileLoader.readListFromFile(file);
			// Create a list to sort
            List<Integer> treeList = new ArrayList<>();
            for (String item : list) {
                getCurrentCarry(treeList, item);
            }
            Collections.sort(treeList);
            // Solution 1 : The most
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

    private void getCurrentCarry(List<Integer> treeList, String item) {
        try {
			// For each line, we convert into int and sum up with the previous one
            int carry = Integer.parseInt(item);
            currentCarry += carry;
        } catch (NumberFormatException e) {
			// If error, we are on a new item
			// We save anf go next item
            treeList.add(currentCarry);
            currentCarry = 0;
        }
    }
}
