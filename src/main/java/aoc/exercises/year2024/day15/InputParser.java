package aoc.exercises.year2024.day15;

import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<Aquarium> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Aquarium parseInput() {
        Aquarium aquarium = new Aquarium();
        for (int i = 0; i < inputList.size(); i++) {
            aquarium.addLine(inputList.get(i), i);
        }
        return aquarium;
    }

    public Biguarium parseSndInput() {
        Biguarium biguarium = new Biguarium();
        for (int i = 0; i < inputList.size(); i++) {
            biguarium.addLine(inputList.get(i), i);
        }
        return biguarium;
    }
}
