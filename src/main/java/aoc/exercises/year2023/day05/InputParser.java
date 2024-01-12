package aoc.exercises.year2023.day05;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<Almanach> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    public Almanach parseInput() {
        String seedsStr = inputList.get(0).replace("seeds: ", "");
        List<Long> seeds = Arrays.stream(seedsStr.split(" "))
                .map(Long::parseLong)
                .toList();

        List<Category> categories = new ArrayList<>();
        String source = null;
        String destination = null;

        Category category = null;
        for (int i = 2; i < inputList.size(); i++) {
            String line = inputList.get(i);
            if (source == null && destination == null) {
                String[] split = line.replace(" map:", "").split("-to-");
                source = split[0];
                destination = split[1];
                category = new Category(source, destination);
                categories.add(category);
            } else if (line.isEmpty()) {
                source = null;
                destination = null;
            } else {
                String[] split = line.split(" ");
                long destinationNumber = Long.parseLong(split[0]);
                long sourceNumber = Long.parseLong(split[1]);
                long range = Long.parseLong(split[2]);
                category.add(sourceNumber, destinationNumber, range);
            }
        }

        return new Almanach(seeds, categories);
    }
}