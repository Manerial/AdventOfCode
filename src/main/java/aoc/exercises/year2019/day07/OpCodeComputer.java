package aoc.exercises.year2019.day07;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OpCodeComputer {
    @Setter
    private int computerInput; // used in saveInInput function
    @Getter
    private int computerOutput = 0; // used in saveInInput function
    private final List<Integer> memory; // The memory of the computer.
    private int positionInMemory = 0; // The position in the memory.
    @Getter
    private boolean finished = false; // True if the computer has reach a 99 OpCode.
    private final boolean waitForInput; // If true, the computer will wait for the next input from another computer.
    @Setter
    private Integer amplifier; // The amplifier to use for this computer.

    public OpCodeComputer(List<Integer> memory, int amplifier, boolean waitForInput) {
        this.memory = new ArrayList<>(memory);
        this.amplifier = amplifier;
        this.waitForInput = waitForInput;
    }

    public void compute() {
        int step;
        // Go through the memory using OpCodes.
        // Each opCode uses a certain number of steps, that we use to increment our position in the memory.
        for (; positionInMemory < memory.size(); positionInMemory += step) {
            int code = memory.get(positionInMemory);
            OpCodeParam opCodeParam = new OpCodeParam(code);

            // OverrideSteps is used to override the number of steps that we use to increment our position in the memory.
            // This is useful when we want to jump at another positions in the memory.
            Integer overrideSteps = null;
            switch (opCodeParam.getOpCode()) {
                case ADD -> add(opCodeParam);
                case MULTIPLY -> multiply(opCodeParam);
                case SAVE_INPUT_AT_ADDRESS -> saveInputAtAddress();
                case SAVE_IN_OUTPUT -> {
                    saveInOutput(opCodeParam);
                    if (waitForInput) {
                        positionInMemory += opCodeParam.getOpCode().steps;
                        return;
                    }
                }
                case JUMP_IF_TRUE -> overrideSteps = jumpIfTrue(opCodeParam);
                case JUMP_IF_FALSE -> overrideSteps = jumpIfFalse(opCodeParam);
                case LESS_THAN -> lessThan(opCodeParam);
                case EQUALS -> equals(opCodeParam);
                case END -> {
                    finished = true;
                    return;
                }
                default -> throw new OutOfMemoryError();
            }
            step = (overrideSteps != null) ? overrideSteps : opCodeParam.getOpCode().steps;
        }
        throw new IndexOutOfBoundsException();
    }

    private void add(OpCodeParam opCodeParam) {
        int param1 = opCodeParam.findParam(memory, positionInMemory, 1);
        int param2 = opCodeParam.findParam(memory, positionInMemory, 2);
        int writeTo = memory.get(getWriteAddress(positionInMemory, OpCode.ADD));
        memory.set(writeTo, param1 + param2);
    }

    private void multiply(OpCodeParam opCodeParam) {
        int param1 = opCodeParam.findParam(memory, positionInMemory, 1);
        int param2 = opCodeParam.findParam(memory, positionInMemory, 2);
        int writeTo = memory.get(getWriteAddress(positionInMemory, OpCode.MULTIPLY));
        memory.set(writeTo, param1 * param2);
    }

    private void saveInputAtAddress() {
        int writeTo = memory.get(getWriteAddress(positionInMemory, OpCode.SAVE_INPUT_AT_ADDRESS));
        if (amplifier != null) {
            memory.set(writeTo, amplifier);
            amplifier = null;
        } else {
            memory.set(writeTo, computerInput);
        }
    }

    private void saveInOutput(OpCodeParam opCodeParam) {
        computerOutput = opCodeParam.findParam(memory, positionInMemory, 1);
    }

    private int jumpIfTrue(OpCodeParam opCodeParam) {
        int param = opCodeParam.findParam(memory, positionInMemory, 1);
        int jumpTo = opCodeParam.findParam(memory, positionInMemory, 2);
        if (param != 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -positionInMemory + jumpTo;
        }
        return opCodeParam.getOpCode().steps;
    }

    private int jumpIfFalse(OpCodeParam opCodeParam) {
        int param = opCodeParam.findParam(memory, positionInMemory, 1);
        int jumpTo = opCodeParam.findParam(memory, positionInMemory, 2);
        if (param == 0) {
            // -position so we can jump to the start of the memory, +jumpTo to jump to the desired position.
            return -positionInMemory + jumpTo;
        }
        return opCodeParam.getOpCode().steps;
    }

    private void lessThan(OpCodeParam opCodeParam) {
        int param1 = opCodeParam.findParam(memory, positionInMemory, 1);
        int param2 = opCodeParam.findParam(memory, positionInMemory, 2);
        int writeTo = memory.get(getWriteAddress(positionInMemory, OpCode.LESS_THAN));
        memory.set(writeTo, param1 < param2 ? 1 : 0);
    }

    private void equals(OpCodeParam opCodeParam) {
        int param1 = opCodeParam.findParam(memory, positionInMemory, 1);
        int param2 = opCodeParam.findParam(memory, positionInMemory, 2);
        int writeTo = memory.get(getWriteAddress(positionInMemory, OpCode.EQUALS));
        memory.set(writeTo, param1 == param2 ? 1 : 0);
    }

    private int getWriteAddress(int position, OpCode opCode) {
        return position + opCode.steps - 1;
    }
}
