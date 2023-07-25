package aoc.exercises.year2015.day02;

import lombok.AllArgsConstructor;

import java.util.stream.IntStream;

@AllArgsConstructor
public class Present {
    private int length;
    private int width;
    private int height;

    /**
     * <pre>
     * Compute the amount of wrapping paper to use for a present.
     * Use its 3 measures, length, with and height.
     * This is represented by :
     * the surface of the present (that's a cube) + the minimal surface for the slack
     * </pre>
     *
     * @return The amount of paper needed to cover the present.
     */
    public int getPaper() {
        int top = length * width;
        int right = length * height;
        int front = width * height;
        int minimalSurface = IntStream.of(top, right, front).min().orElse(0);
        return 2 * top + 2 * right + 2 * front + minimalSurface;
    }


    /**
     * <pre>
     * Compute the amount of ribbon to use for a present.
     * Use its 3 measures, length, with and height.
     * This is represented by :
     * the two minimal length of the present + the cubic feet of volume for the bow
     * </pre>
     *
     * @return The amount of paper needed to cover the present.
     */
    public int getRibbon() {
        int bow = length * width * height;
        int maximalDimension = IntStream.of(length, width, height).max().orElse(0);
        int wrap = (length + width + height - maximalDimension) * 2;
        return bow + wrap;
    }
}
