package aoc.exercises.year2024.day08;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<AntennaMap> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public AntennaMap parseInput() {
        AntennaMap antennaMap = new AntennaMap();
        for (int y = 0; y < inputList.size(); y++) {
            antennaMap.addLineAtY(inputList.get(y), y);
        }
        return antennaMap;
    }
}
