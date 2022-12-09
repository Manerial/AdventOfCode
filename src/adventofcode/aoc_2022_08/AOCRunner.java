package adventofcode.aoc_2022_08;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AOCRunner implements AOC {
    private int maxVisibility = 0;

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<String> listTransposed = new ArrayList<>(list);
            listTransposed = listTransposed.stream().map(e -> "").collect(Collectors.toList());
            for (String item : list) {
                int i = 0;
                for (char c : item.toCharArray()) {
                    listTransposed.set(i, listTransposed.get(i) + c);
                    i++;
                }
            }

            int count = getVisibleTree(list, listTransposed);
            Printer.println("Solution 1 : " + count);

            Printer.println("Solution 2 : " + maxVisibility);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getVisibleTree(List<String> list, List<String> listTransposed) {
        int count = (list.get(0).length() - 1) * 4;
        for (int i = 1; i < list.size() - 1; i++) {
            String item = list.get(i);
            for (int j = 1; j < item.length() - 1; j++) {
                char currentTree = item.charAt(j);
                String treesNorth = listTransposed.get(j).substring(0, i);
                String treesSouth = listTransposed.get(j).substring(i + 1);
                String treesEast = item.substring(0, j);
                String treesWest = item.substring(j + 1);

                boolean isVisible = currentTree > treesNorth.chars().max().getAsInt() ||
                        currentTree > treesSouth.chars().max().getAsInt() ||
                        currentTree > treesEast.chars().max().getAsInt() ||
                        currentTree > treesWest.chars().max().getAsInt();
                if (isVisible) {
                    count++;
                }

                int visibility = getTotalVisibility(currentTree, treesNorth, treesSouth, treesEast, treesWest);
                if (visibility > maxVisibility) {
                    maxVisibility = visibility;
                }
            }
        }
        return count;
    }

    private int getTotalVisibility(char currentTree, String treesNorth, String treesSouth, String treesEast, String treesWest) {
        int visibilityNorth;
        int visibilitySouth;
        int visibilityEast;
        int visibilityWest;
        StringBuilder stringBuilder = new StringBuilder();
        treesNorth = stringBuilder.append(treesNorth).reverse().toString();
        stringBuilder = new StringBuilder();
        treesEast = stringBuilder.append(treesEast).reverse().toString();
        visibilityNorth = getVisibility(currentTree, treesNorth);
        visibilitySouth = getVisibility(currentTree, treesSouth);
        visibilityEast = getVisibility(currentTree, treesEast);
        visibilityWest = getVisibility(currentTree, treesWest);
        Printer.println(visibilityNorth + " " + visibilitySouth + " " + visibilityEast + " " + visibilityWest);
        return visibilityNorth * visibilitySouth * visibilityEast * visibilityWest;
    }

    private int getVisibility(char currentTree, String trees) {
        int visibility = 0;
        for (char c : trees.toCharArray()) {
            visibility++;
            if (c >= currentTree) {
                break;
            }
        }
        return visibility;
    }
}
