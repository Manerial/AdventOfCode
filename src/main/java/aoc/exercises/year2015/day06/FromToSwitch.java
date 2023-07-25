package aoc.exercises.year2015.day06;

import aoc.common_objects.Position;

public record FromToSwitch(Position from, Position to, SwitchLight switchLight) {
    /**
     * Switch the lights depending on the switch light.
     *
     * @param lightGrid : a 2D array of integers representing the light grid
     * @param x         : the x coordinate of the light
     * @param y         : the y coordinate of the light
     */
    public void switchLights(int[][] lightGrid, int x, int y) {
        switch (switchLight()) {
            case ON -> lightGrid[x][y] = 1;
            case OFF -> lightGrid[x][y] = 0;
            case TOGGLE -> lightGrid[x][y] = lightGrid[x][y] == 0 ? 1 : 0;
        }
    }

    /**
     * Power switch the lights depending on the switch light.
     * Power switch means the lights stores the sum of times they were turned on / off / toggled.
     *
     * @param lightGrid : a 2D array of integers representing the light grid
     * @param x         : the x coordinate of the light
     * @param y         : the y coordinate of the light
     */
    public void powerSwitchLights(int[][] lightGrid, int x, int y) {
        switch (switchLight()) {
            case ON -> lightGrid[x][y] += 1;
            case OFF -> lightGrid[x][y] -= lightGrid[x][y] > 0 ? 1 : 0;
            case TOGGLE -> lightGrid[x][y] += 2;
        }
    }
}
