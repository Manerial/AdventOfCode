package aoc.exercises.year2022.day08;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private int maxVisibility = 0;

    @Override
    public void run() {
        List<String> listTransposed = new ArrayList<>(inputList);
        listTransposed = listTransposed.stream().map(e -> "").collect(Collectors.toList());
        for (String item : inputList) {
            int i = 0;
            for (char c : item.toCharArray()) {
                listTransposed.set(i, listTransposed.get(i) + c);
                i++;
            }
        }

        solution1 = getVisibleTree(inputList, listTransposed);
        solution2 = maxVisibility;
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

                boolean isVisible = currentTree > treesNorth.chars().max().orElse(0) ||
                        currentTree > treesSouth.chars().max().orElse(0) ||
                        currentTree > treesEast.chars().max().orElse(0) ||
                        currentTree > treesWest.chars().max().orElse(0);
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
