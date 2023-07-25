package aoc.exercises.year2021.day01;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> intList = inputList.stream()
                .map(Integer::parseInt)
                .toList();
        solution1 = countGreaterMeasure(intList, 1);
        solution2 = countGreaterMeasure(intList, 3);
    }

    private int countGreaterMeasure(List<Integer> list, int glide) {
        int count = 0;
        int last = 0;
        List<Integer> glider = new ArrayList<>();
        for (int item : list) {
            glider.add(item);
            if (glider.size() > glide) {
                glider.remove(0);
                int current = glider.stream().reduce(0, Integer::sum);
                if (last < current) {
                    count++;
                }
                last = current;
            }
        }
        return count;
    }
}
