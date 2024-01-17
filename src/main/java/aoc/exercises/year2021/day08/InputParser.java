package aoc.exercises.year2021.day08;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<List<DigitSignal>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<DigitSignal> parseInput() {
        List<DigitSignal> signals = new java.util.ArrayList<>();
        for (String line : inputList) {
            String[] split = line.split(" \\| ");
            List<String> signalPatterns = Arrays.stream(split[0].split(" ")).toList();
            List<String> outputSignals = Arrays.stream(split[1].split(" ")).toList();
            signals.add(new DigitSignal(signalPatterns, outputSignals));
        }
        return signals;
    }
}
