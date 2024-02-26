package aoc.exercises.year2019.day09;

import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        Map<Long, Long> memory = new HashMap<>();
        Stream.of(inputList.get(0).split(","))
                .forEach(s -> memory.put((long) memory.size(), Long.parseLong(s)));
        OpCodeComputer opCodeComputer = new OpCodeComputer(memory);

        opCodeComputer.setComputerInput(1);
        opCodeComputer.compute();
        solution1 = opCodeComputer.getComputerOutput();

        opCodeComputer.reset();
        opCodeComputer.setComputerInput(2);
        opCodeComputer.compute();
        solution2 = opCodeComputer.getComputerOutput();
    }
}