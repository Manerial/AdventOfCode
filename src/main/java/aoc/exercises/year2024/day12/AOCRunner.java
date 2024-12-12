package aoc.exercises.year2024.day12;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/12">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<CropField> cropFields = inputParser.parseInput();
        solution1 = cropFields.stream().mapToLong(CropField::getPrice).sum();
        solution2 = cropFields.stream().mapToLong(CropField::getPriceWithDiscount).sum(); // > 799424
    }
}