package aoc.exercises.year2024.day17;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Computer {
    @Getter
    private long registerA;
    private long registerB;
    private long registerC;
    @Getter
    private List<Integer> program;
    private int index;

    public void reset(long registerA) {
        this.registerA = registerA;
        registerB = 0;
        registerC = 0;
        index = 0;
    }

    public List<Integer> compute() {
        List<Integer> output = new ArrayList<>();
        while (index < program.size()) {
            Integer currentOutput = executeInstruction();
            if (currentOutput != null) {
                output.add(currentOutput);
            }
            index += 2;
        }
        return output;
    }

    private Integer executeInstruction() {
        int opcode = program.get(index);
        return switch (opcode) {
            case 0 -> {
                divide_A();
                yield null;
            }
            case 1 -> {
                bitwiseXOR_B();
                yield null;
            }
            case 2 -> {
                operandModulo8_B();
                yield null;
            }
            case 3 -> {
                jumpIfNotZero();
                yield null;
            }
            case 4 -> {
                bitwiseXOR_BC();
                yield null;
            }
            case 5 -> output();
            case 6 -> {
                divide_B();
                yield null;
            }
            case 7 -> {
                divide_C();
                yield null;
            }
            default -> throw new IllegalArgumentException("Invalid opcode: " + opcode);
        };
    }

    private long getComboOperand(int index) {
        int value = program.get(index);
        return switch (value) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private long getLitteralOperand(int index) {
        return program.get(index);
    }

    /**
     * The adv instruction (opcode 0) performs division.
     * The numerator is the value in the A register.
     * The denominator is found by raising 2 to the power of the instruction's combo operand.
     * (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.)
     * <p>
     * The result of the division operation is truncated to an integer and then written to the A register.
     */
    private void divide_A() {
        registerA = (long) (registerA / Math.pow(2, getComboOperand(index + 1)));
    }

    /**
     * The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand.
     * <p>
     * The result of the XOR is then stored in register B.
     */
    private void bitwiseXOR_B() {
        registerB = registerB ^ getLitteralOperand(index + 1);
    }

    /**
     * The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.
     */
    private void operandModulo8_B() {
        registerB = getComboOperand(index + 1) % 8;
    }

    /**
     * The jnz instruction (opcode 3) does nothing if the A register is 0.
     * However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand;
     * if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
     */
    private void jumpIfNotZero() {
        if (registerA != 0) {
            index = (int) getLitteralOperand(index + 1) - 2;
        }
    }

    /**
     * The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B.
     * (For legacy reasons, this instruction reads an operand but ignores it.)
     */
    private void bitwiseXOR_BC() {
        registerB = registerB ^ registerC;
    }

    /**
     * The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value.
     * (If a program outputs multiple values, they are separated by commas.)
     *
     * @return the output value
     */
    private Integer output() {
        return (int) (getComboOperand(index + 1) % 8);
    }

    /**
     * The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register.
     * (The numerator is still read from the A register.)
     */
    private void divide_B() {
        registerB = (long) (registerA / Math.pow(2, getComboOperand(index + 1)));
    }

    /**
     * The bdv instruction (opcode 7) works exactly like the adv instruction except that the result is stored in the C register.
     * (The numerator is still read from the A register.)
     */
    private void divide_C() {
        registerC = (long) (registerA / Math.pow(2, getComboOperand(index + 1)));
    }

    public int getProgramSize() {
        return program.size();
    }
}
