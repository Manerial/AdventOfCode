package adventofcode.aoc_2022_09;

import lombok.Data;

@Data
public class Position {
    private int x = 0;
    private int y = 0;

    public void incX() {
        x++;
    }
    public void decX() {
        x--;
    }

    public void incY() {
        y++;
    }
    public void decY() {
        y--;
    }
}
