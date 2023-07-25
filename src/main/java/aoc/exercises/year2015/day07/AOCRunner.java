package aoc.exercises.year2015.day07;

import utilities.AbstractAOC;

import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Map<String, BinaryOperation> operationByWire;

    @Override
    public void run() {
        String finalWire = "a";
        InputParser inputParser = new InputParser(inputList);
        operationByWire = inputParser.parseInput();

        solution1 = resolveRecursive(finalWire);

        operationByWire.forEach((s, binaryOperation) -> binaryOperation.setValue(null));
        operationByWire.get("b").setValue((int) solution1);

        solution2 = resolveRecursive(finalWire);
    }

    /**
     * Resolves the circuit by recursively traversing the wires and applying the operations.
     *
     * @param wire : the wire to resolve
     * @return the value of the wire
     */
    private int resolveRecursive(String wire) {
        BinaryOperation binaryOperation = operationByWire.get(wire);

        if (binaryOperation == null) { // We are on a digit
            return Integer.parseInt(wire);
        } else if (binaryOperation.getValue() != null) { // Avoid already done operations
            return binaryOperation.getValue();
        } else {
            switch (binaryOperation.getOperator()) {
                case AND -> { // A AND B
                    int andLeft = resolveRecursive(binaryOperation.getLeft());
                    int andRight = resolveRecursive(binaryOperation.getRight());
                    binaryOperation.setValue(andLeft & andRight);
                }
                case OR -> { // A OR B
                    int orLeft = resolveRecursive(binaryOperation.getLeft());
                    int orRight = resolveRecursive(binaryOperation.getRight());
                    binaryOperation.setValue(orLeft | orRight);
                }
                case LSHIFT -> { // A LSHIFT 2
                    int paramLShift = resolveRecursive(binaryOperation.getLeft());
                    binaryOperation.setValue(paramLShift << binaryOperation.getRightValue());
                }
                case RSHIFT -> { // A RSHIFT 2
                    int paramRShift = resolveRecursive(binaryOperation.getLeft());
                    binaryOperation.setValue(paramRShift >> binaryOperation.getRightValue());
                }
                case NOT -> { // NOT A
                    int paramNot = resolveRecursive(binaryOperation.getRight());
                    binaryOperation.setValue(65536 + ~paramNot);
                }
                case ASSIGN -> { // ANYTHING -> A
                    if (binaryOperation.isLeftNumeric()) {
                        binaryOperation.setValue(binaryOperation.getLeftValue());
                    } else {
                        binaryOperation.setValue(resolveRecursive(binaryOperation.getLeft()));
                    }
                }
                default ->
                        throw new IllegalArgumentException("Argument " + binaryOperation.getOperator() + " is undefined");
            }
            return binaryOperation.getValue();
        }
    }
}
