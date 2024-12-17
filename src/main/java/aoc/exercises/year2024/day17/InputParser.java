package aoc.exercises.year2024.day17;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<Computer> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Computer parseInput() {
        long registerA = Integer.parseInt(inputList.get(0).split(": ")[1]);
        long registerB = Integer.parseInt(inputList.get(1).split(": ")[1]);
        long registerC = Integer.parseInt(inputList.get(2).split(": ")[1]);
        List<Integer> program = Arrays.stream(inputList.get(4).split(": ")[1].split(","))
                .map(Integer::parseInt)
                .toList();
        return new Computer(registerA, registerB, registerC, program, 0);
    }
}
