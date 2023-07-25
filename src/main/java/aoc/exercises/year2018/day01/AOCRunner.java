package aoc.exercises.year2018.day01;

import utilities.AbstractAOC;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> list = inputList.stream().map(item -> {
            char symbol = item.charAt(0);
            int number = Integer.parseInt(item.substring(1));
            return (symbol == '+') ? number : -number;
        }).toList();

        solution1 = list.stream().mapToInt(Integer::intValue).sum();

        Integer firstFetch = null;
        Set<Integer> steps = new HashSet<>();
        steps.add(0);
        int sum = 0;
        int index = 0;
        while (firstFetch == null) {
            sum += list.get(index);
            if(steps.contains(sum)) {
                firstFetch = sum;
            }
            steps.add(sum);
            index = (index + 1) % list.size();
        }

        solution2 = firstFetch;
    }
}