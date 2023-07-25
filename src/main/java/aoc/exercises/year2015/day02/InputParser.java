package aoc.exercises.year2015.day02;


import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<Present>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Present> parseInput() {
        List<Present> presents = new ArrayList<>();
        for (String item : inputList) {
            String[] dimensions = item.split("x");
            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);
            Present present = new Present(length, width, height);
            presents.add(present);
        }
        return presents;
    }
}
