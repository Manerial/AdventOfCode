package aoc.exercises.year2023.day08;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Ghost {
    private final List<String> steps = new ArrayList<>();
    private String lastStep;
    private int loopSteps = 0;

    public void addStep(String step) {
        lastStep = step.substring(0, 3);
        if (!steps.contains(step)) {
            steps.add(step);
        } else {
            loopSteps = steps.size() - steps.indexOf(step);
        }
    }

    public boolean isFinished() {
        return loopSteps != 0;
    }
}
