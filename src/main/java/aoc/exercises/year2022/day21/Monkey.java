package aoc.exercises.year2022.day21;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
public class Monkey {
    private final String name;
    private final String operation;
    @Setter
    private Long result;
    private Monkey monkey1;
    private Monkey monkey2;
    private String operand;

    public Monkey(String s) {
        String[] split = s.split(": ");
        name = split[0];
        operation = split[1];
    }

    public void setMonkeys(List<Monkey> monkeys) {
        if (StringUtils.isNumeric(operation)) {
            result = Long.parseLong(operation);
        } else {
            String[] split = operation.split(" ");
            String monkeyName1 = split[0];
            String monkeyName2 = split[2];
            monkey1 = monkeys.stream()
                    .filter(m -> m.getName().equals(monkeyName1))
                    .findFirst()
                    .orElse(null);
            monkey2 = monkeys.stream()
                    .filter(m -> m.getName().equals(monkeyName2))
                    .findFirst()
                    .orElse(null);
            operand = split[1];
        }
    }

    public double computeResult() {
        if (result != null) {
            return result;
        }
        return operate();
    }

    public double operate() {
        switch (operand) {
            case "+" -> {
                return monkey1.computeResult() + monkey2.computeResult();
            }
            case "-" -> {
                return monkey1.computeResult() - monkey2.computeResult();
            }
            case "*" -> {
                return monkey1.computeResult() * monkey2.computeResult();
            }
            case "/" -> {
                return monkey1.computeResult() / monkey2.computeResult();
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public double compare() {
        double result1 = monkey1.computeResult();
        double result2 = monkey2.computeResult();
        return result1 - result2;
    }
}
