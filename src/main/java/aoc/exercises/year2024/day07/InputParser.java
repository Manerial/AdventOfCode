package aoc.exercises.year2024.day07;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<List<Equation>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Equation> parseInput() {
        return inputList.stream()
                .map(Equation::new)
                .toList();
    }
}
