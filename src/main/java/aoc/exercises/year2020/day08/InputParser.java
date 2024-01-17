package aoc.exercises.year2020.day08;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<Program> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Program parseInput() {
        Program program = new Program();
        for (String line : inputList) {
            // nop +0
            String[] split = line.split(" ");
            Code code = Code.valueOf(split[0].toUpperCase());
            int value = Integer.parseInt(split[1]);
            Instruction instruction = new Instruction(code, value, false);
            program.addInstruction(instruction);
        }
        return program;
    }
}
