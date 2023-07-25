package aoc.exercises.year2022.day06;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        for (String item : inputList) {
            solution1 = findFirstPacket(item, 4);
            solution2 = findFirstPacket(item, 14);
        }
    }

    private int findFirstPacket(String item, int size) {
        int incr = 0;
        while (incr < item.length() - size) {
            String subStr = item.substring(incr, incr + size);
            long count = subStr.chars().distinct().count();
            if (count == size) {
                return incr + size;
            }
            incr++;
        }
        return 0;
    }
}
