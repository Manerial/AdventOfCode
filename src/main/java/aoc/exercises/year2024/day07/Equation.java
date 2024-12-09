package aoc.exercises.year2024.day07;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Equation {
    private long awaitedResult;
    private boolean alreadyMatchedWithoutConcat;
    private List<Long> operators = new ArrayList<>();
    private boolean useConcat;

    public Equation(String equationStr) {
        String[] parts = equationStr.split(": ");
        awaitedResult = Long.parseLong(parts[0].trim());
        String[] operatorsLst = parts[1].trim().split(" ");
        for (String operatorStr : operatorsLst) {
            operators.add(Long.parseLong(operatorStr));
        }
    }

    public long evaluate() {
        useConcat = false;
        if (matchAwaitedResult(0, 0)) {
            alreadyMatchedWithoutConcat = true;
            return awaitedResult;
        } else {
            return 0;
        }
    }

    public long evaluateWithConcat() {
        useConcat = true;
        if (alreadyMatchedWithoutConcat || matchAwaitedResult(0, 0)) {
            return awaitedResult;
        } else {
            return 0;
        }
    }

    private boolean matchAwaitedResult(long result, int operatorIndex) {
        if (operatorIndex == operators.size()) {
            return awaitedResult == result;
        } else {
            if (matchAwaitedResult(result + operators.get(operatorIndex), operatorIndex + 1)) {
                return true;
            } else if (matchAwaitedResult(result * operators.get(operatorIndex), operatorIndex + 1)) {
                return true;
            } else if (useConcat) {
                long concatenatedOperator = getConcatenatedLongs(result, operators.get(operatorIndex));
                return matchAwaitedResult(concatenatedOperator, operatorIndex + 1);
            }
            return false;
        }
    }

    private long getConcatenatedLongs(Long operator1, Long operator2) {
        return Long.parseLong(operator1.toString() + operator2.toString());
    }
}
