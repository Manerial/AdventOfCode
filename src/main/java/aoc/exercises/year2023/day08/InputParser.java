package aoc.exercises.year2023.day08;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<DesertMap> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public DesertMap parseInput() {
        List<Character> directions = inputList.get(0).chars().mapToObj(c -> (char) c).toList();
        DesertMap desertMap = new DesertMap(directions);
        for (int i = 2; i < inputList.size(); i++) {
            String[] split = inputList.get(i).replace(")", "").split("( = \\(|, )");
            desertMap.addDesertNode(split[0], split[1], split[2]);
        }
        return desertMap;
    }
}
