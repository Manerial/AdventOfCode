package aoc.exercises.year2019.day05;

import lombok.Getter;

import java.util.List;

/**
 * Contains the necessary information to run the OpCode.
 * Replaces the previous ParametersRecord.
 */
@Getter
public class OpCodeParam {
    private final OpCode opCode;
    private final int modeP1;
    private final int modeP2;
    private final int modeP3;

    public OpCodeParam(int codeParam) {
        // Fill the code param with 0, so it matches 5 digits.
        String codeStr = String.format("%05d", codeParam);
        // Yes, they are order-reversed
        modeP3 = Character.getNumericValue(codeStr.charAt(0));
        modeP2 = Character.getNumericValue(codeStr.charAt(1));
        modeP1 = Character.getNumericValue(codeStr.charAt(2));
        // Opcode is on 2 digits
        int opCodeValue = Integer.parseInt(codeStr.substring(3));
        opCode = OpCode.fromValue(opCodeValue);
    }

    /**
     * Find the value of the param, related to its number
     *
     * @param memory           : The current state of the memory.
     * @param positionInMemory : The position in the memory where we want to find the param.
     * @param paramNumber      : The number of the param we want to find, useful to get its mode.
     * @return the desired value of the param, depending on its mode.
     */
    public int findParamValue(List<Integer> memory, int positionInMemory, int paramNumber) {
        int valueAtPosition = memory.get(positionInMemory + paramNumber);
        // Mode == 0 Position mode : We shall return the value at the position valueAtPosition.
        // Mode == 1 Immediate mode : We shall return immediately valueAtPosition.
        switch (paramNumber) {
            case 1 -> {
                return (modeP1 == 0) ? memory.get(valueAtPosition) : valueAtPosition;
            }
            case 2 -> {
                return (modeP2 == 0) ? memory.get(valueAtPosition) : valueAtPosition;
            }
            case 3 -> {
                return (modeP3 == 0) ? memory.get(valueAtPosition) : valueAtPosition;
            }
            default -> throw new IllegalArgumentException("Illegal param value : " + paramNumber);
        }
    }
}