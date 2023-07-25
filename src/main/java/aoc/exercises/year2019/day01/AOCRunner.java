package aoc.exercises.year2019.day01;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> list = inputList.stream()
                .map(Integer::parseInt)
                .toList();
        solution1 = sumList(operateOnList(list));

        solution2 = operateRecursive(list);
    }

    private int operateRecursive(List<Integer> list) {
        List<Integer> newList = operateOnList(list);
        int sum = sumList(newList);
        if (sum != 0) {
            sum += operateRecursive(newList);
        }
        return sum;
    }

    private int sumList(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Integer> operateOnList(List<Integer> list) {
        return list.stream()
                .map(integer -> {
                    int sum = integer / 3 - 2;
                    return Math.max(sum, 0);
                })
                .toList();
    }
}