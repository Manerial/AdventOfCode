package aoc.exercises.year2021.day09;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<int[][]> {

    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public int[][] parseInput() {
        int[][] heightMap = new int[inputList.size()][inputList.get(0).length()];
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < inputList.get(0).length(); j++) {
                heightMap[i][j] = Character.getNumericValue(inputList.get(i).charAt(j));
            }
        }
        return heightMap;
    }
}
