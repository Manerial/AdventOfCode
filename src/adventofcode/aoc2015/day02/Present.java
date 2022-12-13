package adventofcode.aoc2015.day02;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Present {
    private int length;
    private int width;
    private int height;
    public int getPaper() {
        int lw = length*width;
        int lh = length*height;
        int wh = width*height;
        int min = Integer.min(Integer.min(lw, lh), wh);
        return 2*lw + 2*lh + 2*wh + min;
    }

}
