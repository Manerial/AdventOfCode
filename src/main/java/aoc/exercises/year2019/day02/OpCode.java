package aoc.exercises.year2019.day02;

public enum OpCode {
    ADD(1, 4),
    MULTIPLY(2, 4),
    END(99, 0);

    private final int code;
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