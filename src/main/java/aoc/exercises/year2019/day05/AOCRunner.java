package aoc.exercises.year2019.day05;

import utilities.AbstractAOC;

import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> memory = Stream.of(inputList.get(0).split(","))
                .map(Integer::parseInt)
                .toList();
        OpCodeComputer opCodeComputer = new OpCodeComputer(memory);

        opCodeComputer.setComputerInput(1);
        opCodeComputer.compute();
        solution1 = opCodeComputer.getComputerOutput();

        opCodeComputer.setComputerInput(5);
        opCodeComputer.compute();
        solution2 = opCodeComputer.getComputerOutput();
    }
}