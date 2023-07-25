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
        solution1 = countGreaterMeasure(inputList, 1);
        solution2 = countGreaterMeasure(inputList, 3);
    }

    private int countGreaterMeasure(List<String> list, int glide) {
        int count = 0;
        int last = 0;
        List<Integer> glider = new ArrayList<>();
        for (String item : list) {
            glider.add(Integer.parseInt(item));
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
