package aoc.exercises.year2024.day23;

import utilities.AbstractAOC;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/23">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        Map<String, LanComputer> computersByName = new HashMap<>();
        for (String line : inputList) {
            String[] computerNames = line.split("-");
            String leftComputerName = computerNames[0];
            String rightComputerName = computerNames[1];

            LanComputer leftComputer = computersByName.getOrDefault(leftComputerName, new LanComputer(leftComputerName));
            LanComputer rightComputer = computersByName.getOrDefault(rightComputerName, new LanComputer(rightComputerName));

            leftComputer.addConnectedComputer(rightComputer);
            rightComputer.addConnectedComputer(leftComputer);

            computersByName.put(leftComputerName, leftComputer);
            computersByName.put(rightComputerName, rightComputer);
        }

        solution1 = computersByName.values().stream()
                .map(LanComputer::getLoopOf3ConnectedComputers)
                .flatMap(Collection::stream)
                .filter(loopOf3 -> loopOf3.stream().anyMatch(computerName -> computerName.startsWith("t")))
                .collect(Collectors.toSet())
                .size();

        Set<Set<LanComputer>> alreadyTreated = new HashSet<>();
        solution2 = String.join(",",
                computersByName.values().parallelStream()
                        .map(lanComputer -> lanComputer.getLargestLoop(alreadyTreated))
                        .max(Comparator.comparingInt(Set::size))
                        .orElseThrow()
                        .stream()
                        .sorted()
                        .toList()
        );
    }
}