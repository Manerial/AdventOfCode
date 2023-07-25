package aoc.exercises.year2021.day04;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<InputParser.Result> {

    public record Result(List<Integer> draws, List<Bingo> grids) {
    }

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Result parseInput() {
        List<Integer> draws = parseDraws();
        List<Bingo> grids = parseGrids();
        return new Result(draws, grids);
    }


    private List<Integer> parseDraws() {
        String[] drawsLine = inputList.get(0).split(",");
        List<Integer> draws = Arrays.stream(drawsLine).map(Integer::parseInt).toList();
        // We want a modifiable list
        return new ArrayList<>(draws);
    }

    private List<Bingo> parseGrids() {
        List<Bingo> grids = new ArrayList<>();
        List<String> gridLines = new ArrayList<>();
        for (String str : inputList.subList(2, inputList.size())) {
            if (!str.isEmpty()) {
                gridLines.add(str);
            } else {
                grids.add(new Bingo(gridLines));
                gridLines = new ArrayList<>();
            }
        }
        grids.add(new Bingo(gridLines));
        return grids;
    }
}
