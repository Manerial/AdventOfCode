package adventofcode.aoc2022.day12;

import lombok.Data;
import utilities.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class HeightMap {
    private final Map<Position, Cell> grid = new HashMap<>();
    private boolean isFromStart = true;

    public void addCell(Position position, Cell cell) {
        grid.put(position, cell);
    }

    public void calculateGridFromStartAndEnd() {
        calculateGridDistances();
        isFromStart = false;
        calculateGridDistances();
    }

    private void calculateGridDistances() {
        int cellDistanceToCalculate = 0;
        int limitDistance = grid.size();
        while(gridNotEntirelyCalculated() && cellDistanceToCalculate <= limitDistance) {
            List<Cell> cellsToCalculate = getCellsWithDistanceOf(cellDistanceToCalculate);
            for (Cell cell : cellsToCalculate) {
                calculateNeighborDistance(cell);
            }
            cellDistanceToCalculate++;
        }
    }

    private boolean gridNotEntirelyCalculated() {
        return grid.values().stream().anyMatch(cell -> cell.getDistance(isFromStart) == -1);
    }

    private List<Cell> getCellsWithDistanceOf(int cellDistanceToCalculate) {
        return grid.values().stream()
                .filter(cell -> cell.getDistance(isFromStart) == cellDistanceToCalculate)
                .collect(Collectors.toList());
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
        if(isFromStart) {
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
        Position maxPosition = grid.keySet().stream().reduce(Position::max).orElse(new Position(0, 0));
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y <= maxPosition.getY(); y++) {
            for (int x = 0; x <= maxPosition.getX(); x++) {
                Position p = new Position(x, y);
                Cell cell = grid.get(p);
                stringBuilder.append("\t").append(cell.getDistance(isFromStart));
            }
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}
