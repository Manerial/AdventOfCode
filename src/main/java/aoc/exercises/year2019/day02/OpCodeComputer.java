package aoc.exercises.year2019.day02;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class OpCodeComputer {
    @Getter
    private int computerOutput = 0;
    private final List<Integer> memory;

    /**
     * Create a new ParametersRecord.
     * ParametersRecord contains all the values necessary to run the OpCode.
     *
     * @param opCode        : the OpCode to execute.
     * @param outputAddress : The memory address where we will store the Operation's output.
     * @param valueAtNoun   : The value we have to use as first parameter.
     * @param valueAtVerb   : The value we have to use as second parameter.
     */
    record ParametersRecord(OpCode opCode, int outputAddress, int valueAtNoun, int valueAtVerb) {
    }

    public OpCodeComputer(List<Integer> memory) {
        this.memory = memory;
    }

    /**
     * Run the computer until the given output is reached.
     *
     * @param awaited : the given output
     * @return the computer's output'
     */
    public int computeUntilAwaited(int awaited) {
        int noun = 0;
        int verb = 0;
        while (true) {
            compute(noun, verb);
            if (computerOutput == awaited) {
                return noun * 100 + verb;
            } else if (noun < verb) {
                noun++;
            } else {
                verb++;
                noun = 0;
            }
        }
    }

    /**
     * Run the computer with the given valueAtNoun and valueAtVerb.
     *
     * @param noun : The valueAtNoun will replace the second value in the memory.
     * @param verb : The valueAtVerb will replace the third value in the memory.
     */
    public void compute(int noun, int verb) {
        // Copy the memory so we don't alter the original one.
        List<Integer> currentMemory = new ArrayList<>(memory);
        currentMemory.set(1, noun);
        currentMemory.set(2, verb);
        int step;
        // Go through the memory using OpCodes.
        // Each opCode uses a certain number of steps, that we use to increment our position in the memory.
        for (int blocPosition = 0; blocPosition < currentMemory.size(); blocPosition += step) {
            ParametersRecord parametersRecord = getParameters(currentMemory, blocPosition);
            if (parametersRecord.opCode == OpCode.END) {
                computerOutput = currentMemory.get(0);
                return;
            }
            switch (parametersRecord.opCode) {
                case ADD -> add(currentMemory, parametersRecord);
                case MULTIPLY -> multiply(currentMemory, parametersRecord);
                default -> throw new OutOfMemoryError();
            }
            step = parametersRecord.opCode.steps;
        }
    }

    /**
     * Get the computer's
     *
     * @param currentMemory : The current state of our memory.
     * @param blocPosition  : The bloc position to read the current OpCode
     * @return the ParametersRecord at blocPosition
     */
    private ParametersRecord getParameters(List<Integer> currentMemory, int blocPosition) {
        OpCode opCode = OpCode.fromValue(currentMemory.get(blocPosition));
        if (opCode == OpCode.END) {
            return new ParametersRecord(opCode, 0, 0, 0);
        }
        // CurrentNoun and CurrentVerb are addresses.
        int currentNoun = currentMemory.get(blocPosition + 1);
        int currentVerb = currentMemory.get(blocPosition + 2);
        int outputAddress = currentMemory.get(blocPosition + 3);
        // We shall compute on values we find on Noun and Verb.
        int valueAtNoun = currentMemory.get(currentNoun);
        int valueAtVerb = currentMemory.get(currentVerb);
        return new ParametersRecord(opCode, outputAddress, valueAtNoun, valueAtVerb);
    }

    private void add(List<Integer> currentMemory, ParametersRecord parametersRecord) {
        currentMemory.set(parametersRecord.outputAddress, parametersRecord.valueAtNoun + parametersRecord.valueAtVerb);
    }

    private void multiply(List<Integer> currentMemory, ParametersRecord parametersRecord) {
        currentMemory.set(parametersRecord.outputAddress, parametersRecord.valueAtNoun * parametersRecord.valueAtVerb);
    }
}
