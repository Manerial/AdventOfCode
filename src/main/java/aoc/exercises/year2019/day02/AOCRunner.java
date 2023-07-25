package aoc.exercises.year2019.day02;

import utilities.AbstractAOC;

import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    public static final int AWAITED_OUTPUT = 19690720;

    @Override
    public void run() {
        List<Integer> memory = Stream.of(inputList.get(0).split(","))
                .map(Integer::parseInt)
                .toList();

        OpCodeComputer opCodeComputer = new OpCodeComputer(memory);
        if(isExample) {
            opCodeComputer.compute(9, 10);
            solution1 = opCodeComputer.getComputerOutput();
            solution2 = 1202; // Cannot be tested
        } else {
            opCodeComputer.compute(12, 2);
            solution1 = opCodeComputer.getComputerOutput();
            solution2 = opCodeComputer.computeUntilAwaited(AWAITED_OUTPUT);
        }
    }
}