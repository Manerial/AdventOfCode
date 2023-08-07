package aoc.exercises.year2017.day08;

import utilities.AbstractAOC;

import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Map<String, Integer> registers;
    private int maxValue = 0;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        registers = inputParser.parseInput();
        compute();
        solution1 = registers.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
        solution2 = maxValue;
    }

    private void compute() {
        for (String command : inputList) {
            String[] splitLine = command.split(" ");
            String register = splitLine[0];
            String operation = splitLine[1];
            int operand = Integer.parseInt(splitLine[2]);
            String condRegister = splitLine[4];
            String condition = splitLine[5];
            int condValue = Integer.parseInt(splitLine[6]);

            if (operation.equals("dec")) {
                operand = -operand;
            }

            int newValue = getRegisterValue(register, operand, condRegister, condition, condValue);

            registers.put(register, newValue);
        }
    }

    private int getRegisterValue(String register, int operand, String condRegister, String condition, int condValue) {
        int newValue = getRegister(register);
        switch (condition) {
            case ">" -> {
                if (getRegister(condRegister) > condValue) {
                    newValue += operand;
                }
            }
            case "<" -> {
                if (getRegister(condRegister) < condValue) {
                    newValue += operand;
                }
            }
            case ">=" -> {
                if (getRegister(condRegister) >= condValue) {
                    newValue += operand;
                }
            }
            case "<=" -> {
                if (getRegister(condRegister) <= condValue) {
                    newValue += operand;
                }
            }
            case "!=" -> {
                if (getRegister(condRegister) != condValue) {
                    newValue += operand;
                }
            }
            case "==" -> {
                if (getRegister(condRegister) == condValue) {
                    newValue += operand;
                }
            }
            default -> throw new UnsupportedOperationException(condition + " is not a valid condition");
        }
        maxValue = Math.max(maxValue, newValue);
        return newValue;
    }

    private int getRegister(String register) {
        return registers.get(register);
    }
}
