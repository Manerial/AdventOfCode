package aoc.exercises.year2017.day05;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> instructions = initList();
        solution1 = mazeSolve(instructions.toArray(new Integer[0]), false);
        solution2 = mazeSolve(instructions.toArray(new Integer[0]), true);
    }

    private List<Integer> initList() {
        List<Integer> instructions = new ArrayList<>();
        for (String line : inputList) {
            int instruction = Integer.parseInt(line);
            instructions.add(instruction);
        }
        return instructions;
    }

    private int mazeSolve(Integer[] instructions, boolean crazySolving) {
        int index = 0;
        int steps = 0;
        while (index < instructions.length) {
            int instruction = instructions[index];
            if (crazySolving && instruction >= 3) {
                instructions[index]--;
            } else {
                instructions[index]++;
            }
            index += instruction;
            steps++;
        }
        return steps;
    }
}