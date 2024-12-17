package aoc.exercises.year2024.day17;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/17">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Computer computer = inputParser.parseInput();
        List<Integer> output = computer.compute();
        solution1 = getJoin(output);

        long answer = 1;
        for (int i = computer.getProgram().size() - 1; i >= 0; i--) {
            while (!output.get(0).equals(computer.getProgram().get(i))) {
                answer++;
                computer.reset(answer);
                output = computer.compute();
            }
            if (output.equals(computer.getProgram())) {
                break;
            }
            answer *= 8;
            computer.reset(answer);
            output = computer.compute();
        }
        solution2 = answer;
    }

    private static String getJoin(List<Integer> output) {
        return String.join(",", output.stream().map(String::valueOf).toList());
    }
}