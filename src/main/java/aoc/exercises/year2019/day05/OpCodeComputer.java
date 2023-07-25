package aoc.exercises.year2019.day05;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OpCodeComputer {
    @Setter
    private int computerInput; // used in saveInInput function
    @Getter
    private int computerOutput = 0; // used in saveInInput function
    private final List<Integer> memory;

    public OpCodeComputer(List<Integer> memory) {
        this.memory = memory;
    }


    /**
     * Run the computer
     */
    public void compute() {
        // Copy the memory so we don't alter the original one.
        List<Integer> currentMemory = new ArrayList<>(memory);
        int step;
        // Go through the memory using OpCodes.
        // Each opCode uses a certain number of steps, that we use to increment our position in the memory.
        for (int position = 0; position < currentMemory.size(); position += step) {
            int code = currentMemory.get(position);
            OpCodeParam opCodeParam = new OpCodeParam(code);

            // OverrideSteps is used to override the number of steps that we use to increment our position in the memory.
            // This is useful when we want to jump at another positions in the memory.
            Integer overrideSteps = null;
            switch (opCodeParam.getOpCode()) {
                case ADD -> add(opCodeParam, currentMemory, position);
                case MULTIPLY -> multiply(opCodeParam, currentMemory, position);
                case SAVE_INPUT_AT_ADDRESS -> saveInputAtAddress(currentMemory, position);
                case SAVE_IN_OUTPUT -> saveInOutput(opCodeParam, currentMemory, position);
                case JUMP_IF_TRUE -> overrideSteps = jumpIfTrue(opCodeParam, currentMemory, position);
                case JUMP_IF_FALSE -> overrideSteps = jumpIfFalse(opCodeParam, currentMemory, position);
                case LESS_THAN -> lessThan(opCodeParam, currentMemory, position);
                case EQUALS -> equals(opCodeParam, currentMemory, position);
                case END -> {
                    return;
                }
                default -> throw new OutOfMemoryError();
            }
            step = (overrideSteps != null) ? overrideSteps : opCodeParam.getOpCode().steps;
        }
        throw new IndexOutOfBoundsException();
    }

    private void add(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param1 = opCodeParam.findParamValue(currentMemory, position, 1);
        int param2 = opCodeParam.findParamValue(currentMemory, position, 2);
        int writeTo = currentMemory.get(getWriteAddress(position, OpCode.ADD));
        currentMemory.set(writeTo, param1 + param2);
    }

    private void multiply(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param1 = opCodeParam.findParamValue(currentMemory, position, 1);
        int param2 = opCodeParam.findParamValue(currentMemory, position, 2);
        int writeTo = currentMemory.get(getWriteAddress(position, OpCode.MULTIPLY));
        currentMemory.set(writeTo, param1 * param2);
    }

    private void saveInputAtAddress(List<Integer> currentMemory, int position) {
        int writeTo = currentMemory.get(getWriteAddress(position, OpCode.SAVE_INPUT_AT_ADDRESS));
        currentMemory.set(writeTo, computerInput);
    }

    private void saveInOutput(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        computerOutput = opCodeParam.findParamValue(currentMemory, position, 1);
    }

    private int jumpIfTrue(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param = opCodeParam.findParamValue(currentMemory, position, 1);
        int jumpTo = opCodeParam.findParamValue(currentMemory, position, 2);
        if (param != 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -position + jumpTo;
        }
        return 3;
    }

    private int jumpIfFalse(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param = opCodeParam.findParamValue(currentMemory, position, 1);
        int jumpTo = opCodeParam.findParamValue(currentMemory, position, 2);
        if (param == 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -position + jumpTo;
        }
        return 3;
    }

    private void lessThan(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param1 = opCodeParam.findParamValue(currentMemory, position, 1);
        int param2 = opCodeParam.findParamValue(currentMemory, position, 2);
        int writeTo = currentMemory.get(getWriteAddress(position, OpCode.LESS_THAN));
        currentMemory.set(writeTo, param1 < param2 ? 1 : 0);
    }

    private void equals(OpCodeParam opCodeParam, List<Integer> currentMemory, int position) {
        int param1 = opCodeParam.findParamValue(currentMemory, position, 1);
        int param2 = opCodeParam.findParamValue(currentMemory, position, 2);
        int writeTo = currentMemory.get(getWriteAddress(position, OpCode.EQUALS));
        currentMemory.set(writeTo, param1 == param2 ? 1 : 0);
    }

    private int getWriteAddress(int position, OpCode opCode) {
        return position + opCode.steps - 1;
    }
}
