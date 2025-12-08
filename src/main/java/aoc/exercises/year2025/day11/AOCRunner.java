package aoc.exercises.year2025.day11;

import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<String, Node> nodes = inputParser.parseInput();

        solution1 = nodes.get("you").getPathsTo("out", List.of());

        long i = nodes.get("svr").getPathsTo("fft", List.of());
        long j = nodes.get("fft").getPathsTo("dac", List.of());
        long k = nodes.get("dac").getPathsTo("out", List.of());

        long l = nodes.get("svr").getPathsTo("dac", List.of());
        long m = nodes.get("dac").getPathsTo("fft", List.of());
        long n = nodes.get("fft").getPathsTo("out", List.of());
        solution2 = i * j * k + l * m * n;
    }
}