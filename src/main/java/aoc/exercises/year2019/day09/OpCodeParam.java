package aoc.exercises.year2019.day09;

import lombok.Getter;

import java.util.Map;

/**
 * Contains the necessary information to run the OpCode.
 */
@Getter
public class OpCodeParam {
    private final OpCode opCode;
    private final ParamMode modeP1;
    private final ParamMode modeP2;
    private final ParamMode modeP3;

    public OpCodeParam(long codeParam) {
        // Fill the code param with 0, so it matches 5 digits.
        String codeStr = String.format("%05d", codeParam);
        // Yes, they are order-reversed
        modeP3 = ParamMode.fromValue(Character.getNumericValue(codeStr.charAt(0)));
        modeP2 = ParamMode.fromValue(Character.getNumericValue(codeStr.charAt(1)));
        modeP1 = ParamMode.fromValue(Character.getNumericValue(codeStr.charAt(2)));
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
    public long findParamValue(Map<Long, Long> memory, long positionInMemory, int paramNumber, long relativeBase) {
        long valueAtPosition = memory.getOrDefault(positionInMemory + paramNumber, 0L);
        switch (paramNumber) {
            case 1 -> {
                return getValue(memory, valueAtPosition, modeP1, relativeBase);
            }
            case 2 -> {
                return getValue(memory, valueAtPosition, modeP2, relativeBase);
            }
            case 3 -> {
                return getValue(memory, valueAtPosition, modeP3, relativeBase);
            }
            default -> throw new IllegalArgumentException("Illegal param value : " + paramNumber);
        }
    }

    private long getValue(Map<Long, Long> memory, long valueAtPosition, ParamMode modeP1, long relativeBase) {
        // Position mode : We shall return the value at the position valueAtPosition.
        // Immediate mode : We shall return immediately valueAtPosition.
        // Relative mode :
        switch (modeP1) {
            case POSITION -> {
                return memory.getOrDefault(valueAtPosition, 0L);
            }
            case IMMEDIATE -> {
                return valueAtPosition;
            }
            case RELATIVE -> {
                return memory.getOrDefault(valueAtPosition + relativeBase, 0L);
            }
            default -> throw new IllegalArgumentException("Illegal param value : " + modeP1);
        }
    }

    public long getDestinationAddress(Map<Long, Long> memory, long position, long relativeBase) {
        ParamMode mode;
        switch (opCode.steps - 1) {
            case 1 -> mode = modeP1;
            case 2 -> mode = modeP2;
            case 3 -> mode = modeP3;
            default -> throw new IllegalArgumentException("Illegal param value : " + (opCode.steps - 1));
        }
        long destinationAddress;
        switch (mode) {
            case POSITION -> destinationAddress = memory.get(position + opCode.steps - 1);
            case RELATIVE -> destinationAddress = memory.get(position + opCode.steps - 1) + relativeBase;
            default -> throw new IllegalArgumentException("Illegal param value : " + mode);
        }
        return destinationAddress;
    }
}