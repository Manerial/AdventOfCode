package aoc.exercises.year2015.day09;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<Town>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Town> parseInput() {
        List<Town> towns = new ArrayList<>();
        inputList.stream()
                .map(line -> List.of(line.split(" ")[0], line.split(" ")[2]))
                .flatMap(List::stream)
                .distinct()
                .forEach(townName -> towns.add(new Town(townName)));
        for (String line : inputList) {
            String[] split = line.split(" ");
            String town1name = split[0];
            String town2name = split[2];
            int distance = Integer.parseInt(split[4]);
            Town town1 = towns.stream().filter(t -> t.is(town1name)).findAny().orElseThrow();
            Town town2 = towns.stream().filter(t -> t.is(town2name)).findAny().orElseThrow();
            town1.add(town2, distance);
            town2.add(town1, distance);
        }
        return towns;
    }
}
