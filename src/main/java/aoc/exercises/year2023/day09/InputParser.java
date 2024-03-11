package aoc.exercises.year2023.day09;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<List<PredictableHistory>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<PredictableHistory> parseInput() {
        List<PredictableHistory> historyList = new ArrayList<>();
        for (String line : inputList) {
            List<Integer> history = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            historyList.add(new PredictableHistory(history));
        }
        return historyList;
    }
}
