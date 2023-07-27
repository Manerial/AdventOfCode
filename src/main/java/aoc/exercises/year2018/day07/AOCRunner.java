package aoc.exercises.year2018.day07;

import utilities.AbstractAOC;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Set<Step> steps = new TreeSet<>(inputParser.parseInput());

        solution1 = getResolutionChain(steps);

        solution2 = getTimeToExecute(steps);
    }

    private String getResolutionChain(Set<Step> steps) {
        Set<Step> validatedStep = new LinkedHashSet<>(); // Save the order

        while (validatedStep.size() != steps.size()) {
            saveNextStep(steps, validatedStep);
        }

        return validatedStep.stream()
                .map(Step::getName)
                .collect(Collectors.joining());
    }

    private void saveNextStep(Set<Step> steps, Set<Step> validatedStep) {
        for(Step step : steps) {
            if(validatedStep.contains(step)) {
                continue;
            }
            Set<Step> stepsToValidate = step.getStepsToValidate();
            if(validatedStep.containsAll(stepsToValidate)) {
                validatedStep.add(step);
                return;
            }
        }
    }

    private int getTimeToExecute(Set<Step> steps) {
        int time = 0;
        while (!isWorkEnded(steps)) {
            Set<Step> endedSteps = steps.stream()
                    .filter(Step::isFinished)
                    .collect(Collectors.toSet());

            steps.stream()
                    .filter(step -> !step.isFinished())
                    .sorted(Comparator.comparingInt(Step::getWorkedTime).reversed())
                    .filter(step -> endedSteps.containsAll(step.getStepsToValidate()))
                    .limit(5)
                    .forEach(Step::work);
            time++;
        }
        return time;
    }

    private boolean isWorkEnded(Set<Step> steps) {
        boolean workEnded;
        long countFinished = steps.stream().filter(Step::isFinished).count();
        workEnded = countFinished == steps.size();
        return workEnded;
    }
}
