package aoc.exercises.year2024.day12;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<List<CropField>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<CropField> parseInput() {
        Map<Position, Character> field = new HashMap<>();
        for (int y = 0; y < inputList.size(); y++) {
            String row = inputList.get(y);
            for (int x = 0; x < row.length(); x++) {
                field.put(new Position(x, y), row.charAt(x));
            }
        }

        List<Position> alreadyTreatedCrops = new ArrayList<>();
        List<CropField> cropFields = new ArrayList<>();
        field.keySet().forEach(anyCrop -> {
            if (!alreadyTreatedCrops.contains(anyCrop)) {
                CropField cropField = new CropField(field, anyCrop);
                cropFields.add(cropField);
                alreadyTreatedCrops.addAll(cropField.getField());
            }
        });
        return cropFields;
    }
}
