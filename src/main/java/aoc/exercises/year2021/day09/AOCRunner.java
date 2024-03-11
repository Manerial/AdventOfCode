package aoc.exercises.year2021.day09;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        int[][] heightMap = inputParser.parseInput();
        List<Position> listOfRiskPoints = getListOfRiskPoints(heightMap);
        solution1 = listOfRiskPoints.stream()
                .mapToInt(value -> heightMap[value.getX()][value.getY()] + 1)
                .sum();
        List<List<Position>> basins = getBasins(heightMap, listOfRiskPoints);
        solution2 = basins.stream()
                .map(List::size)
                .sorted(Collections.reverseOrder())
                .limit(3)
                .reduce((integer, integer2) -> integer * integer2)
                .orElseThrow();
    }

    /**
     * Get the local minimums of a grid of numbers
     *
     * @param heightMap : the grid of numbers to compute
     * @return the local minimums of a grid of numbers
     */
    private List<Position> getListOfRiskPoints(int[][] heightMap) {
        List<Position> listOfRiskPoints = new ArrayList<>();
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[i].length; j++) {
                int current = heightMap[i][j];
                int up = (j - 1 >= 0) ? heightMap[i][j - 1] : 9;
                int down = (j + 1 < heightMap[i].length) ? heightMap[i][j + 1] : 9;
                int left = (i - 1 >= 0) ? heightMap[i - 1][j] : 9;
                int right = (i + 1 < heightMap.length) ? heightMap[i + 1][j] : 9;

                if (current < up && current < down && current < left && current < right) {
                    listOfRiskPoints.add(new Position(i, j));
                }
            }
        }
        return listOfRiskPoints;
    }

    /**
     * Get the basins (the list of neighbors positions where the height is below 9) of the grid of numbers
     *
     * @param heightMap        : the grid of numbers to compute
     * @param listOfRiskPoints : the local minimums of the grid of numbers
     * @return the basins of the grid
     */
    private List<List<Position>> getBasins(int[][] heightMap, List<Position> listOfRiskPoints) {
        List<List<Position>> basins = new ArrayList<>();
        for (Position position : listOfRiskPoints) {
            basins.add(getBasin(heightMap, position, new ArrayList<>()));
        }
        return basins;
    }

    /**
     * Recursively get the basin (the list of neighbors positions where the height is below 9) of the current position
     *
     * @param heightMap : the grid of numbers to compute
     * @param position  : the current position
     * @param basin     : the current basin
     * @return the basin of the current position
     */
    private List<Position> getBasin(int[][] heightMap, Position position, List<Position> basin) {
        if (basin.contains(position) ||
                position.getX() >= heightMap.length ||
                position.getX() < 0 ||
                position.getY() >= heightMap[0].length ||
                position.getY() < 0 ||
                heightMap[position.getX()][position.getY()] == 9
        ) {
            return new ArrayList<>();
        }
        basin.add(position);
        getBasin(heightMap, new Position(position.getX() - 1, position.getY()), basin);
        getBasin(heightMap, new Position(position.getX() + 1, position.getY()), basin);
        getBasin(heightMap, new Position(position.getX(), position.getY() - 1), basin);
        getBasin(heightMap, new Position(position.getX(), position.getY() + 1), basin);
        return basin;
    }
}