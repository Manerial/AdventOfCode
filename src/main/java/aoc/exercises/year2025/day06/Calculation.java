package aoc.exercises.year2025.day06;

import lombok.*;

import java.util.*;

@Setter
@Getter
public class Calculation {
    private List<String> operandsInLine = new ArrayList<>();
    private List<String> operandsInColumn = new ArrayList<>();
    private String operation;

    public void addOperandInColumn(String operand) {
        operandsInColumn.add(operand.trim());
        char[] charArray = operand.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (operandsInLine.size() <= i) {
                operandsInLine.add(String.valueOf(charArray[i]).trim());
            } else {
                String newOperand = operandsInLine.get(i) + charArray[i];
                operandsInLine.set(i, newOperand.trim());
            }
        }
    }

    public long calculateLine() {
        return calculate(operandsInLine);
    }

    public long calculateColumn() {
        return calculate(operandsInColumn);
    }

    private long calculate(List<String> operands) {
        switch (operation) {
            case "+" -> {
                return operands.stream().mapToLong(Long::parseLong).sum();
            }
            case "*" -> {
                return operands.stream().mapToLong(Long::parseLong).reduce((left, right) -> left * right).orElse(0);
            }
            default -> {
                return 0;
            }
        }
    }
}
