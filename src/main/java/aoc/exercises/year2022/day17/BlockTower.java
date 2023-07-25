package aoc.exercises.year2022.day17;

import aoc.common_objects.Position;
import aoc.common_objects.Shape;

import java.util.LinkedList;
import java.util.List;

public class BlockTower {
    private static final int WIDTH = 7;
    private final List<Position> blocks = new LinkedList<>();

    public BlockTower(List<Position> bottom) {
        blocks.addAll(bottom);
    }

    public int size() {
        return blocks.stream()
                .map(Position::getY)
                .max(Integer::compareTo)
                .orElse(0);
    }

    public void add(Shape currentShape) {
        blocks.addAll(currentShape.getPositions());
    }

    public List<Position> getBlocks() {
        return blocks;
    }

    public int getWidth() {
        return WIDTH;
    }
}
