package aoc.exercises.year2019.day06;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<Planet> {
    private String firstPlanetName;

    public InputParser(List<String> inputList, String firstPlanetName) {
        super(inputList);
        this.firstPlanetName = firstPlanetName;
    }

    @Override
    public Planet parseInput() {
        List<String> sortedList = sortInputRecursive(firstPlanetName);

        Planet firstPlanet = new Planet(firstPlanetName);
        for (String line : sortedList) {
            String[] elts = line.split("\\)");
            Planet planet = firstPlanet.getOrbital(elts[0]);
            Planet orbital = new Planet(elts[1]);
            planet.addOrbital(orbital);
        }
        return firstPlanet;
    }

    private List<String> sortInputRecursive(String startName) {
        List<String> listContainingName = inputList.stream().filter(line -> line.contains(startName + ")")).toList();
        List<String> sortedList = new ArrayList<>(listContainingName);

        listContainingName.forEach(s -> {
            String nextName = s.split("\\)")[1];
            sortedList.addAll(sortInputRecursive(nextName));
        });

        return sortedList;
    }

}
