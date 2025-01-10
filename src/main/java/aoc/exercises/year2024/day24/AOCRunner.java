package aoc.exercises.year2024.day24;

import utilities.AbstractAOC;

import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/24">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<String, BooleanGate> gatesByNames = inputParser.parseInput();
        solution1 = getGatesSolution(gatesByNames, "z");
        if (isExample) {
            solution2 = 0; // Example is not compliant
        } else {
            // Find wrong gates :
            // Get all Z gates, then test X and Y (4 tests) and check the result
            // For all wrong Z gates, get all the intermediates gates
            // Bruteforce the gates switches
            solution2 = 0; // TODO: implement solution2 for real input
        }
    }

    private static long getGatesSolution(Map<String, BooleanGate> gatesByNames, String startWith) {
        String gatesSolutionsStr = gatesByNames.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(startWith))
                .sorted(Map.Entry.<String, BooleanGate>comparingByKey().reversed())
                .map(entry -> entry.getValue().getOutput() ? "1" : "0")
                .reduce(String::concat)
                .orElseThrow();
        System.out.println(gatesSolutionsStr);
        return Long.parseLong(gatesSolutionsStr, 2);
    }
}