package aoc.exercises.year2022.day24;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cell {
    private boolean populated = false;
    private boolean wall = false;
    private final List<Character> winds = new ArrayList<>();

    public Cell() {
    }

    public Cell(char cellContent) {
        switch (cellContent) {
            case '.' -> {/*return*/}
            case '#' -> wall = true;
            default -> winds.add(cellContent);
        }
    }

    public Cell cloneCellWithoutWind() {
        Cell newCell = new Cell();
        newCell.wall = this.wall;
        newCell.populated = populated;
        return newCell;
    }

    public void populate() {
        populated = true;
    }

    public void unPopulate() {
        populated = false;
    }

    public boolean hasWind() {
        return !winds.isEmpty();
    }

    public void addWind(Character wind) {
        this.getWinds().add(wind);
    }
}
