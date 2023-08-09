package aoc.exercises.year2019.day08;

import java.util.ArrayList;
import java.util.List;

import static utilities.ResourceIO.DELIMITER;

public class Layer {
    private final List<List<Integer>> pixelLines = new ArrayList<>();
    private final int width;
    private final int height;

    public Layer(int width, int height) {
        this.width = width;
        this.height = height;
        pixelLines.add(new ArrayList<>());
    }

    public void addPixel(int pixel) {
        if(getLastPixelLine().size() == width) {
            pixelLines.add(new ArrayList<>());
        }
        getLastPixelLine().add(pixel);
    }

    private List<Integer> getLastPixelLine() {
        return pixelLines.get(pixelLines.size() - 1);
    }

    public boolean isComplete() {
        return pixelLines.size() == height && getLastPixelLine().size() == width;
    }

    public int countIntegers(int value) {
        return pixelLines.stream()
                .mapToInt(pixels -> Math.toIntExact(pixels.stream().filter(pixel -> pixel == value).count()))
                .sum();
    }

    public int getPixelAt(int x, int y) {
        return pixelLines.get(y).get(x);
    }

    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(getPixelAt(x, y) == 1 ? "#" : " ");
            }
            sb.append(DELIMITER);
        }
        return sb.toString();
    }
}
