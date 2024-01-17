package aoc.exercises.year2020.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Instruction {
    private Code code;
    private int value;
    @Setter
    private boolean alreadyExecuted;

    public Instruction(Instruction instruction) {
        this.code = instruction.getCode();
        this.value = instruction.getValue();
    }

    public boolean changeCode() {
        if (code == Code.NOP) {
            code = Code.JMP;
            return true;
        } else if (code == Code.JMP) {
            code = Code.NOP;
            return true;
        }
        return false;
    }
}
