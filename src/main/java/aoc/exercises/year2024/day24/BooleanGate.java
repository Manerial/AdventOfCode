package aoc.exercises.year2024.day24;

import lombok.Data;

@Data
public class BooleanGate {
    private final String name;
    private Operator operator;
    private BooleanGate gate1;
    private BooleanGate gate2;
    private Boolean output;

    public boolean getOutput() {
        if (output != null) {
            return output;
        }
        if (gate1 == null || gate2 == null) {
            throw new IllegalStateException("Cannot compute output : one or both gates are null");
        }
        output = switch (operator) {
            case AND -> gate1.getOutput() && gate2.getOutput();
            case OR -> gate1.getOutput() || gate2.getOutput();
            case XOR -> (gate1.getOutput() && !gate2.getOutput()) || (!gate1.getOutput() && gate2.getOutput());
        };
        return output;
    }
}
