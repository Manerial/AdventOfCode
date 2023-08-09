package aoc.exercises.year2019.day08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Screen {
    private final List<Layer> layers = new ArrayList<>();
    private final int width;
    private final int height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Layer getLayerWithFewestZero() {
        return layers.stream().min(Comparator.comparingInt(o -> o.countIntegers(0))).orElseThrow();
    }

    public Layer getDisplayedLayer() {
        Layer displayedLayer = new Layer(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int displayedPixel = getDisplayedPixelAt(x, y);
                displayedLayer.addPixel(displayedPixel);
            }
        }
        return displayedLayer;
    }

    private int getDisplayedPixelAt(int x, int y) {
        return layers.stream()
                .map(layer -> layer.getPixelAt(x, y))
                .filter(pixel -> pixel != 2)
                .findFirst()
                .orElseThrow();
    }

    public Layer createLayer() {
        Layer layer = new Layer(width, height);
        layers.add(layer);
        return layer;
    }
}
