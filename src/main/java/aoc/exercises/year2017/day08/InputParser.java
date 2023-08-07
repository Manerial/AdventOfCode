package aoc.exercises.year2017.day08;

import utilities.AbstractInputParser;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InputParser extends AbstractInputParser<Map<String, Integer>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<String, Integer> parseInput() {
        Map<String, Integer> operationMap = new TreeMap<>();
        for (String line : inputList) {
            String[] splitLine = line.split(" ");
            String register = splitLine[0];
            String register2 = splitLine[4];
            operationMap.putIfAbsent(register, 0);
            operationMap.putIfAbsent(register2, 0);
        }
        return operationMap;
    }
}
