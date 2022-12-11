package utilities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Position {
    private int x;
    private int y;

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
        List<Integer> rangeX = Range.toList(p1.x, p2.x);
        List<Integer> rangeY = Range.toList(p1.y, p2.y);
        if (isLine(p1, p2)) {
            rangeX.forEach(x -> positions.add(new Position(x, p1.y)));
            rangeY.forEach(y -> positions.add(new Position(p1.x, y)));
        } else if (isDiagonal(p1, p2)) {
            for (int i = 0; i < rangeX.size(); i++) {
                Position position = new Position(rangeX.get(i), rangeY.get(i));
                positions.add(position);
            }
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
