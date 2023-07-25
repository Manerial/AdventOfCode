package aoc.exercises.year2015.day07;


import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<String, BinaryOperation>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<String, BinaryOperation> parseInput() {
        Map<String, BinaryOperation> operationMap = new HashMap<>();
        for (String line : inputList) {
            String[] splitLine = line.split(" -> ");
            String operationStr = splitLine[0];
            String wire = splitLine[1];
            operationMap.put(wire, new BinaryOperation(operationStr));
        }
        return operationMap;
    }
}
