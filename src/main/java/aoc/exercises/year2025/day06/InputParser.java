package aoc.exercises.year2025.day06;

import utilities.*;

import java.util.*;

public class InputParser extends AbstractInputParser<List<Calculation>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Calculation> parseInput() {
        String[] operations = inputList.getLast().trim().split(" +");
        List<String> operands = inputList.subList(0, inputList.size() - 1);

        List<String> transposition = transpose(operands);
        return toCalculation(transposition, operations);
    }

    private List<String> transpose(List<String> input) {
        List<String> result = new ArrayList<>();
        int maxSize = input.stream().mapToInt(String::length).max().orElseThrow();

        for (int colIndex = 0; colIndex < maxSize; colIndex++) {
            StringBuilder column = new StringBuilder();
            for (String line : input) {
                if (colIndex >= line.length()) {
                    column.append(" ");
                } else {
                    column.append(line.charAt(colIndex));
                }
            }
            result.add(column.toString());
        }
        return result;
    }

    private List<Calculation> toCalculation(List<String> transposition, String[] operations) {
        List<Calculation> result = new ArrayList<>();
        Calculation calculation = new Calculation();
        for (String line : transposition) {
            if (line.isBlank()) {
                calculation.setOperation(operations[result.size()]);
                result.add(calculation);
                calculation = new Calculation();
            } else {
                calculation.addOperandInColumn(line);
            }
        }
        calculation.setOperation(operations[result.size()]);
        result.add(calculation);
        return result;
    }
}
