package aoc.exercises.year2023.day09;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<PredictableHistory> predictableHistoryList = inputParser.parseInput();
        solution1 = predictableHistoryList.stream().mapToInt(PredictableHistory::findNextValue).sum();
        solution2 = predictableHistoryList.stream().mapToInt(PredictableHistory::findPrevValue).sum();
    }
}