package aoc.exercises.year2019.day09;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class OpCodeComputer {
    @Setter
    private long computerInput; // used in saveInInput function
    @Getter
    private long computerOutput = 0; // used in saveInInput function
    private final Map<Long, Long> memory;
    private long relativeBase = 0;

    public OpCodeComputer(Map<Long, Long> memory) {
        this.memory = memory;
    }

    public void reset() {
        this.computerInput = 0;
        this.computerOutput = 0;
        this.relativeBase = 0;
    }

    /**
     * Run the computer
     */
    public void compute() {
        // Copy the memory so we don't alter the original one.
        Map<Long, Long> currentMemory = new HashMap<>(memory);
        long step;
        // Go through the memory using OpCodes.
        // Each opCode uses a certain number of steps, that we use to increment our position in the memory.
        for (long position = 0; position < currentMemory.size(); position += step) {
            long code = currentMemory.get(position);
            OpCodeParam opCodeParam = new OpCodeParam(code);

            // OverrideSteps is used to override the number of steps that we use to increment our position in the memory.
            // This is useful when we want to jump at another positions in the memory.
            Long overrideSteps = null;
            switch (opCodeParam.getOpCode()) {
                case ADD -> add(opCodeParam, currentMemory, position);
                case MULTIPLY -> multiply(opCodeParam, currentMemory, position);
                case SAVE_INPUT_AT_ADDRESS -> saveInputAtAddress(opCodeParam, currentMemory, position);
                case SAVE_IN_OUTPUT -> saveInOutput(opCodeParam, currentMemory, position);
                case JUMP_IF_TRUE -> overrideSteps = jumpIfTrue(opCodeParam, currentMemory, position);
                case JUMP_IF_FALSE -> overrideSteps = jumpIfFalse(opCodeParam, currentMemory, position);
                case LESS_THAN -> lessThan(opCodeParam, currentMemory, position);
                case EQUALS -> equals(opCodeParam, currentMemory, position);
                case ADJUST_RELATIVE_BASE -> adjustRelativeBase(opCodeParam, currentMemory, position);
                case END -> {
                    return;
                }
                default -> throw new OutOfMemoryError();
            }
            step = (overrideSteps != null) ? overrideSteps : opCodeParam.getOpCode().steps;
        }
        throw new IndexOutOfBoundsException();
    }

    private void add(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param1 = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long param2 = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        long writeTo = opCodeParam.getDestinationAddress(currentMemory, position, relativeBase);
        currentMemory.put(writeTo, param1 + param2);
    }

    private void multiply(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param1 = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long param2 = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        long writeTo = opCodeParam.getDestinationAddress(currentMemory, position, relativeBase);
        currentMemory.put(writeTo, param1 * param2);
    }

    private void saveInputAtAddress(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long writeTo = opCodeParam.getDestinationAddress(currentMemory, position, relativeBase);
        currentMemory.put(writeTo, computerInput);
    }

    private void saveInOutput(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        computerOutput = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
    }

    private long jumpIfTrue(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long jumpTo = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        if (param != 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -position + jumpTo;
        }
        return 3;
    }

    private long jumpIfFalse(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long jumpTo = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        if (param == 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -position + jumpTo;
        }
        return 3;
    }

    private void lessThan(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param1 = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long param2 = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        long writeTo = opCodeParam.getDestinationAddress(currentMemory, position, relativeBase);
        currentMemory.put(writeTo, param1 < param2 ? 1L : 0L);
    }

    private void equals(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param1 = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        long param2 = opCodeParam.findParamValue(currentMemory, position, 2, relativeBase);
        long writeTo = opCodeParam.getDestinationAddress(currentMemory, position, relativeBase);
        currentMemory.put(writeTo, param1 == param2 ? 1L : 0L);
    }

    private void adjustRelativeBase(OpCodeParam opCodeParam, Map<Long, Long> currentMemory, long position) {
        long param1 = opCodeParam.findParamValue(currentMemory, position, 1, relativeBase);
        relativeBase += param1;
    }
}
