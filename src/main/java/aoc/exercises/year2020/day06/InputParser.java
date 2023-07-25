package aoc.exercises.year2020.day06;

import utilities.AbstractInputParser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputParser extends AbstractInputParser<List<Set<Character>>> {
    private boolean isAnyYes = true;

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    public void switchParser() {
        isAnyYes = !isAnyYes;
    }

    @Override
    public List<Set<Character>> parseInput() {
        if (isAnyYes) {
            return getAnyYesByGroup();
        } else {
            return getEveryYesByGroup();
        }
    }

    private List<Set<Character>> getAnyYesByGroup() {
        List<Set<Character>> answers = new ArrayList<>();
        Set<Character> currentGroupAnswers = new HashSet<>();
        for (String line : inputList) {
            if (line.isEmpty()) {
                answers.add(currentGroupAnswers);
                currentGroupAnswers = new HashSet<>();
            } else {
                currentGroupAnswers.addAll(line.chars().mapToObj(c -> (char) c).toList());
            }
        }
        answers.add(currentGroupAnswers);
        return answers;
    }

    private List<Set<Character>> getEveryYesByGroup() {
        List<Set<Character>> answers = new ArrayList<>();
        List<Character> currentGroupAnswers = new ArrayList<>();
        int groupSize = 0;
        for (String line : inputList) {
            if (line.isEmpty()) {
                answers.add(getEveryYes(currentGroupAnswers, groupSize));
                currentGroupAnswers = new ArrayList<>();
                groupSize = 0;
            } else {
                groupSize++;
                currentGroupAnswers.addAll(line.chars().mapToObj(c -> (char) c).toList());
            }
        }
        answers.add(getEveryYes(currentGroupAnswers, groupSize));
        return answers;
    }

    private Set<Character> getEveryYes(List<Character> currentGroupAnswers, int groupSize) {
        return currentGroupAnswers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(characterLongEntry -> characterLongEntry.getValue() == groupSize)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
