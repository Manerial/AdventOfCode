package aoc.exercises.year2017.day07;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class Program {

    private final String name;
    private final Set<Program> aboves;
    private final int weight;

    public Program(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.aboves = new HashSet<>();
    }

    public void addAbove(Program program) {
        this.aboves.add(program);
    }

    public int countChilds() {
        int count = aboves.size();
        for (Program program : aboves) {
            count += program.countChilds();
        }
        return count;
    }

    public int getTotalWeight() {
        int totalWeight = weight;
        for (Program program : aboves) {
            totalWeight += program.getTotalWeight();
        }
        return totalWeight;
    }

    public WrongWeightProgram findWrongWeightProgram(int shallBe) {
        List<Integer> weights = aboves.stream().map(Program::getTotalWeight).toList();
        Set<Integer> distinctWeights = new HashSet<>(weights);

        if (distinctWeights.size() == 1) {
            // If true, the current program is the wrong weighted
            return new WrongWeightProgram(this, this.weight - (this.getTotalWeight() - shallBe));
        }

        int wrongWeight = getWrongWeight(weights);
        int correctWeight = getCorrectWeight(distinctWeights, wrongWeight);
        Program wrongWeightProgram = getWrongWeightProgram(wrongWeight);
        return wrongWeightProgram.findWrongWeightProgram(correctWeight);
    }

    private Program getWrongWeightProgram(int wrongWeight) {
        return aboves.stream()
                .filter(program -> program.getTotalWeight() == wrongWeight)
                .findFirst()
                .orElseThrow();
    }

    private static int getWrongWeight(List<Integer> weights) {
        return weights.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    private static Integer getCorrectWeight(Set<Integer> distinctWeights, int wrongWeight) {
        return distinctWeights.stream()
                .filter(integer -> integer != wrongWeight)
                .findFirst()
                .orElseThrow();
    }
}
