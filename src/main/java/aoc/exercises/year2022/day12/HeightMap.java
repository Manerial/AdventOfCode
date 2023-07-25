package aoc.exercises.year2022.day12;

import aoc.common_objects.Position;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.ResourceIO.DELIMITER;

public class HeightMap {
    private final Map<Position, Cell> grid = new HashMap<>();
    private Cell firstCell;
    private Cell lastCell;
    private boolean isFromStart = true;

    public void addCell(Position position, Cell cell) {
        grid.put(position, cell);
    }

    public void calculateGridFromStartAndEnd() {
        setFirstCell();
        setLastCell();

        isFromStart = true;
        calculateGridDistances();
        isFromStart = false;
        calculateGridDistances();
    }

    private void setFirstCell() {
        this.firstCell = grid.values().stream()
                .filter(cell -> cell.getDistanceFromStart() == 0)
                .findFirst()
                .orElseThrow();
    }

    private void setLastCell() {
        this.lastCell = grid.values().stream()
                .filter(cell -> cell.getDistanceFromEnd() == 0)
                .findFirst()
                .orElseThrow();
    }

    private void calculateGridDistances() {
        int cellDistanceToCalculate = 0;
        while (!lastCellComputed()) {
            List<Cell> cellsToCalculate = getCellsWithDistanceOf(cellDistanceToCalculate);
            for (Cell cell : cellsToCalculate) {
                calculateNeighborDistance(cell);
            }
            cellDistanceToCalculate++;
        }
    }

    private boolean lastCellComputed() {
        return isFromStart ?
                lastCell.getDistanceFromStart() != -1 :
                firstCell.getDistanceFromEnd() != -1;
    }

    private List<Cell> getCellsWithDistanceOf(int cellDistanceToCalculate) {
        return grid.values().stream()
                .filter(cell -> cell.getDistance(isFromStart) == cellDistanceToCalculate)
                .toList();
    }

    private void calculateNeighborDistance(Cell cell) {
        setCellDistance(getCellUp(cell), cell);
        setCellDistance(getCellDown(cell), cell);
        setCellDistance(getCellLeft(cell), cell);
        setCellDistance(getCellRight(cell), cell);
    }

    public Cell getCellUp(Cell cell) {
        Position position = new Position(cell.getPosition());
        position.decY();
        return getCell(cell, position);
    }

    public Cell getCellDown(Cell cell) {
        Position position = new Position(cell.getPosition());
        position.incY();
        return getCell(cell, position);
    }

    public Cell getCellLeft(Cell cell) {
        Position position = new Position(cell.getPosition());
        position.decX();
        return getCell(cell, position);
    }

    public Cell getCellRight(Cell cell) {
        Position position = new Position(cell.getPosition());
        position.incX();
        return getCell(cell, position);
    }

    private Cell getCell(Cell cell, Position position) {
        Cell neighbor = grid.get(position);
        if (neighbor != null && checkHeight(cell, neighbor)) {
            return neighbor;
        }
        return null;
    }

    private void setCellDistance(Cell cellToSet, Cell neighbor) {
        if (cellToSet != null && checkHeight(neighbor, cellToSet) && shouldSetDistance(cellToSet, neighbor)) {
            cellToSet.setDistance(neighbor.getDistance(isFromStart) + 1, isFromStart);
        }
    }

    private boolean checkHeight(Cell cell, Cell cellToGo) {
        int heightDistance = cellToGo.getHeight() - cell.getHeight();
        if (isFromStart) {
            return heightDistance < 2;
        } else {
            return heightDistance > -2;
        }
    }

    private boolean shouldSetDistance(Cell cellToSet, Cell neighbor) {
        return cellToSet.getDistance(isFromStart) == -1 || neighbor.getDistance(isFromStart) + 1 < cellToSet.getDistance(isFromStart);
    }

    @Override
    public java.lang.String toString() {
        Position maxPosition = grid.keySet().stream().reduce(Position::maxComparator).orElse(new Position(0, 0));
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y <= maxPosition.getY(); y++) {
            for (int x = 0; x <= maxPosition.getX(); x++) {
                Position p = new Position(x, y);
                Cell cell = grid.get(p);
                stringBuilder.append("\t").append(cell.getDistance(isFromStart));
            }
            stringBuilder.append(DELIMITER);
        }
        return stringBuilder.toString();
    }

    public Cell getLastCell() {
        return lastCell;
    }

    public Cell getNearestCell() {
        return grid.values().stream()
                .filter(cell -> cell.getHeight() == 'a')
                .filter(cell -> cell.getDistanceFromEnd() > -1)
                .min(Comparator.comparingInt(Cell::getDistanceFromEnd))
                .orElseThrow();
    }
}
