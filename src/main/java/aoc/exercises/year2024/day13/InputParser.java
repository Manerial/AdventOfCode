package aoc.exercises.year2024.day13;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<ClawMachina>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<ClawMachina> parseInput() {
        inputList = new ArrayList<>(inputList);
        inputList.add("");
        List<ClawMachina> clawMachinas = new ArrayList<>();
        List<String> machinaSpecs = new ArrayList<>();
        for (String line : inputList) {
            if (line.isBlank()) {
                clawMachinas.add(new ClawMachina(machinaSpecs));
                machinaSpecs.clear();
            } else {
                machinaSpecs.add(line);
            }
        }
        return clawMachinas;
    }
}
