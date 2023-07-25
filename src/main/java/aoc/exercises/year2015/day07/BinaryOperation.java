package aoc.exercises.year2015.day07;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class BinaryOperation {
    private String right;
    private String left;
    private final Operator operator;
    @Setter
    private Integer value;

    public BinaryOperation(String operationStr) {
        String[] splittedOperation = operationStr.split(" ");
        if (splittedOperation.length == 1) { // assign
            left = splittedOperation[0];
            operator = Operator.ASSIGN;
        } else if (splittedOperation.length == 2) { // not
            right = splittedOperation[1];
            operator = Operator.valueOf(splittedOperation[0]);
        } else { // and / or / lshift / rshift
            left = splittedOperation[0];
            operator = Operator.valueOf(splittedOperation[1]);
            right = splittedOperation[2];
        }
    }

    public boolean isLeftNumeric() {
        return StringUtils.isNumeric(left);
    }

    public int getLeftValue() {
        return Integer.parseInt(left);
    }

    public int getRightValue() {
        return Integer.parseInt(right);
    }
}
