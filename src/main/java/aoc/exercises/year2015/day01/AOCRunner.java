package aoc.exercises.year2015.day01;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String line = inputList.get(0);

        solution1 = getFinalFloor(line);
        solution2 = getFirstBasementIndex(line);
    }

    /**
     * Get the final floor that we reach from the input.
     * This is equivalent to the number of open parentheses (floor up)
     * minus the number of closing parentheses (floor down).
     *
     * @param line : the line of the input
     * @return the final floor that we reach from the input.
     */
    private long getFinalFloor(String line) {
        long parenthesesOpen = line.chars().filter(value -> value == '(').count();
        long parenthesesClose = line.length() - parenthesesOpen;
        return parenthesesOpen - parenthesesClose;
    }

    /**
     * Get the first index of the string where we enter the basement.
     * This can be get when the number of closing parentheses (floor down)
     * is greater than the number of open parentheses (floor up) for the first time.
     *
     * @param line : the line of the input
     * @return the first index of the input where we enter the basement.
     */
    private static int getFirstBasementIndex(String line) {
        int basementIndex = 1;
        int floor = 0;
        for (char c : line.toCharArray()) {
            floor += (c == '(') ? 1 : -1;
            if (floor == -1) {
                break;
            }
            basementIndex++;
        }
        return basementIndex;
    }
}