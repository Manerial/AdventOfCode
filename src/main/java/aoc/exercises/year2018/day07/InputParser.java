package aoc.exercises.year2018.day07;

import utilities.AbstractInputParser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<Collection<Step>> {
    Pattern extractSteps = Pattern.compile("Step ([A-Z]).*([A-Z]).*");

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Collection<Step> parseInput() {
        Map<String, Step> stepMap = new HashMap<>();

        inputList.forEach(line -> {
            Matcher matcher = extractSteps.matcher(line);
            if (matcher.find()) {
                Step stepToValidate = getStep(stepMap, matcher.group(1));
                Step step = getStep(stepMap, matcher.group(2));

                step.addStepToValidate(stepToValidate);
            }
        });

        return stepMap.values();
    }

    private static Step getStep(Map<String, Step> stepMap, String stepName) {
        Step step = stepMap.get(stepName);
        if (step == null) {
            step = new Step(stepName);
            stepMap.putIfAbsent(stepName, step);
        }
        return step;
    }
}
