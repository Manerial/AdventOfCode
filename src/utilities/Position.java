package utilities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Position {
    private int x = 0;
    private int y = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    public static List<Position> interval(Position p1, Position p2) {
        List<Position> positions = new ArrayList<>();
        if (isLine(p1, p2)) {
            int xMin = Integer.min(p1.x, p2.x);
            int xMax = Integer.max(p1.x, p2.x);
            int yMin = Integer.min(p1.y, p2.y);
            int yMax = Integer.max(p1.y, p2.y);
            if (xMin != xMax) {
                for (; xMin <= xMax; xMin++) {
                    positions.add(new Position(xMin, p1.y));
                }
            } else {
                for (; yMin <= yMax; yMin++) {
                    positions.add(new Position(p1.x, yMin));
                }
            }
        } else if (isDiagonal(p1, p2)) {


        }
        return positions;
    }

    public static boolean isDiagonal(Position position1, Position position2) {
        int deltaX = Math.abs(position1.getX() - position2.getX());
        int deltaY = Math.abs(position1.getY() - position2.getY());
        return deltaX == deltaY;
    }

    public static boolean isLine(Position position1, Position position2) {
        return position1.getX() == position2.getX() || position1.getY() == position2.getY();
    }
}
