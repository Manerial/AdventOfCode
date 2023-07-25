package aoc.exercises.year2021.day04;

import lombok.Getter;
import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Bingo {

    private final List<BingoCell> cells = new ArrayList<>();
    private boolean win = false;
    private Integer winPlay = null;

    public Bingo(List<String> lines) {
        for (int line = 0; line < lines.size(); line++) {
            List<String> items = Arrays.stream(lines.get(line).split(" "))
                    .filter(s -> !s.isBlank())
                    .toList();
            for (int column = 0; column < items.size(); column++) {
                Position position = new Position(line, column);
                int item = Integer.parseInt(items.get(column));
                BingoCell bingoCell = new BingoCell(position, item, false);
                cells.add(bingoCell);
            }
        }
    }

    public void playAll(List<Integer> draws) {
        for (int drawnNumber : draws) {
            this.play(drawnNumber);
            if (winPlay != null) {
                return;
            }
        }
    }

    private void play(int drawnNumber) {
        cells.stream()
                .filter(cell -> cell.getValue() == drawnNumber)
                .forEach(cell -> {
                    cell.setDrawn(true);
                    win = hasWin(cell.getPosition());
                    if (win) {
                        winPlay = drawnNumber;
                    }
                });
    }

    private boolean hasWin(Position position) {
        // Does current column contains only true ?
        boolean hasColumnWin = !cells.stream()
                .filter(cell -> cell.getPosition().getX() == position.getX())
                .map(BingoCell::isDrawn)
                .toList()
                .contains(false);

        // Does current line contains only true ?
        boolean hasLineWin = !cells.stream()
                .filter(cell -> cell.getPosition().getY() == position.getY())
                .map(BingoCell::isDrawn)
                .toList()
                .contains(false);

        return hasLineWin || hasColumnWin;
    }

    public List<Integer> getNotDrawnContent() {
        return cells.stream()
                .filter(cell -> !cell.isDrawn())
                .map(BingoCell::getValue)
                .toList();
    }
}
