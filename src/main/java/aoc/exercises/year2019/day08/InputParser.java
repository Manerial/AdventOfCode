package aoc.exercises.year2019.day08;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.List;

public class InputParser extends AbstractInputParser<Screen> {

    private final int width;
    private final int height;

    public InputParser(List<String> inputList, int width, int height) {
        super(inputList);
        this.width = width;
        this.height = height;
    }

    @Override
    public Screen parseInput() {
        List<Integer> pixels = Arrays.stream(inputList.get(0).split("")).map(Integer::parseInt).toList();
        Screen screen = new Screen(width, height);
        Layer layer = screen.createLayer();
        for (Integer pixel : pixels) {
            if (layer.isComplete()) {
                layer = screen.createLayer();
            }
            layer.addPixel(pixel);
        }
        return screen;
    }
}
