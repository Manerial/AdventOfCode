package aoc.exercises.year2019.day08;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int width = (isExample) ? 2 : 25;
        int height = (isExample) ? 2 : 6;
        InputParser inputParser = new InputParser(inputList, width, height);
        Screen screen = inputParser.parseInput();
        Layer layerWithFewestZero = screen.getLayerWithFewestZero();
        solution1 = layerWithFewestZero.countIntegers(1) * layerWithFewestZero.countIntegers(2);
        solution2 = screen.getDisplayedLayer().toDisplayString();
    }
}
