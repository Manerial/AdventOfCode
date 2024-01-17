package aoc.exercises.year2020.day08;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Program {
    @Getter
    private int accumulator;
    private int position;
    private List<Instruction> instructions = new ArrayList<>();

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }

    public void debug() {
        List<Instruction> save = instructions.stream().map(Instruction::new).toList();
        for (int instructionIndex = 0; instructionIndex < save.size(); instructionIndex++) {
            resetProgram(save);
            if (instructions.get(instructionIndex).changeCode()) {
                boolean isLoop = executeUntilLoop();
                if (!isLoop) {
                    break;
                }
            }
        }
        instructions = save.stream().map(Instruction::new).toList();
    }

    private void resetProgram(List<Instruction> save) {
        instructions = save.stream().map(Instruction::new).toList();
        accumulator = 0;
        position = 0;
    }

    public boolean executeUntilLoop() {
        while (true) {
            if (position == instructions.size()) {
                return false;
            } else if (instructions.get(position).isAlreadyExecuted()) {
                return true;
            }
            Instruction instruction = instructions.get(position);
            instruction.setAlreadyExecuted(true);
            switch (instruction.getCode()) {
                case ACC -> {
                    accumulator += instruction.getValue();
                    position++;
                }
                case JMP -> position += instruction.getValue();
                case NOP -> position++;
                default -> throw new IllegalStateException("Unexpected value: " + instruction.getCode());
            }
        }
    }
}
