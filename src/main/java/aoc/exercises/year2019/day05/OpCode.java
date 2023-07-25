package aoc.exercises.year2019.day05;

public enum OpCode {
    ADD(1, 4),
    MULTIPLY(2, 4),
    SAVE_INPUT_AT_ADDRESS(3, 2),
    SAVE_IN_OUTPUT(4, 2),
    JUMP_IF_TRUE(5, 3),
    JUMP_IF_FALSE(6, 3),
    LESS_THAN(7, 4),
    EQUALS(8, 4),
    END(99, 0);

    public final int code;
    public final int steps;

    /**
     * @param code  : The code that can be read in the memory.
     * @param steps : The steps that we have to reach after executing the OpCode.
     */
    OpCode(int code, int steps) {
        this.code = code;
        this.steps = steps;
    }

    public static OpCode fromValue(int code) {
        for (OpCode opCode : values()) {
            if (opCode.code == code) {
                return opCode;
            }
        }
        throw new IllegalArgumentException("Il n'y a pas de OpCode pour " + code);
    }
}
