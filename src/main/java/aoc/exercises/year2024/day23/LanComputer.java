package aoc.exercises.year2024.day23;

import lombok.Getter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class LanComputer {
    private final String name;
    private final Set<LanComputer> connectedComputers;

    public LanComputer(String name) {
        this.name = name;
        this.connectedComputers = new HashSet<>();
    }

    public void addConnectedComputer(LanComputer rightComputer) {
        connectedComputers.add(rightComputer);
    }

    public Set<List<String>> getLoopOf3ConnectedComputers() {
        Set<List<String>> loopsOf3 = new HashSet<>();
        for (LanComputer computer1 : connectedComputers) {
            for (LanComputer computer2 : connectedComputers) {
                if (computer1.isConnectedTo(computer2)) {
                    List<String> computersList = Stream.of(this, computer1, computer2)
                            .map(LanComputer::getName)
                            .sorted()
                            .toList();
                    loopsOf3.add(computersList);
                }
            }
        }
        return loopsOf3;
    }

    public Set<String> getLargestLoop(Set<Set<LanComputer>> alreadyTreated) {
        return getLargestLoop(new HashSet<>(), alreadyTreated).stream()
                .map(LanComputer::getName)
                .collect(Collectors.toSet());
    }

    private Set<LanComputer> getLargestLoop(Set<LanComputer> currentLoop, Set<Set<LanComputer>> alreadyTreated) {
        Set<LanComputer> largestLoop = new HashSet<>(currentLoop);
        largestLoop.add(this);
        if (!alreadyTreated.add(largestLoop)) {
            return largestLoop;
        }
        return connectedComputers.stream()
                .filter(computer -> !largestLoop.contains(computer))
                .filter(computer -> computer.connectedComputers.containsAll(currentLoop))
                .map(computer -> computer.getLargestLoop(largestLoop, alreadyTreated))
                .max(Comparator.comparingInt(Set::size))
                .orElse(largestLoop);
    }

    private boolean isConnectedTo(LanComputer computer) {
        return connectedComputers.contains(computer);
    }
}
