package aoc.exercises.year2016.day11;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<RadioisotopeTestingFacility> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public RadioisotopeTestingFacility parseInput() {
        RadioisotopeTestingFacility radioisotopeTestingFacility = new RadioisotopeTestingFacility();
        for (String line : inputList) {
            RTFFloor rtfFloor = new RTFFloor(line);
            radioisotopeTestingFacility.addFloor(rtfFloor);
        }
        return radioisotopeTestingFacility;
    }
}
