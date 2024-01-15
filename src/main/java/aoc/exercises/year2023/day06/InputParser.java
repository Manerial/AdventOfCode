package aoc.exercises.year2023.day06;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<InputParser.Result> {
    public record Result(List<Race> races, Race race) {
    }

    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Result parseInput() {
        List<Race> races = parseInputMultiRace();
        Race race = parseInputSimpleRace();
        return new Result(races, race);
    }

    private List<Race> parseInputMultiRace() {
        List<Race> races = new ArrayList<>();
        String[] times = inputList.get(0).replaceAll("Time:\\s+", "").split("\\s+");
        String[] distances = inputList.get(1).replaceAll("Distance:\\s+", "").split("\\s+");
        for (int i = 0; i < times.length; i++) {
            long time = Long.parseLong(times[i]);
            long distance = Long.parseLong(distances[i]);
            races.add(new Race(time, distance));
        }
        return races;
    }

    private Race parseInputSimpleRace() {
        long time = Long.parseLong(inputList.get(0).replace("Time:", "").replaceAll("\\s", ""));
        long distance = Long.parseLong(inputList.get(1).replace("Distance:", "").replaceAll("\\s", ""));
        return new Race(time, distance);
    }
}
