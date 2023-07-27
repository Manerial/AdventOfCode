package aoc.exercises.year2018.day07;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Step implements Comparable<Step> {
    private static final String ALPHABET = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String name;
    private final Set<Step> stepsToValidate;
    private int workedTime;

    public Step(String name) {
        this.name = name;
        this.stepsToValidate = new HashSet<>();
    }

    public boolean isFinished() {
        return workedTime == 60 + ALPHABET.indexOf(name);
    }

    public void work() {
        workedTime++;
    }

    public void addStepToValidate(Step step) {
        this.stepsToValidate.add(step);
    }

    @Override
    public int compareTo(Step step) {
        return this.name.compareTo(step.name);
    }
}
