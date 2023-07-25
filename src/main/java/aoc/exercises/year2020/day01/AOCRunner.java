package aoc.exercises.year2020.day01;

import utilities.AbstractAOC;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/1">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> list = inputList.stream()
                .map(Integer::parseInt)
                .sorted()
                .toList();

        solution1 = recursiveFunction(list, 2020, 2);
        solution2 = recursiveFunction(list, 2020, 3);
    }

    private int recursiveFunction(List<Integer> list, int target, int nbElements) {
        if (nbElements == 1) {
            if (Collections.binarySearch(list, target) >= 0) {
                return target;
            }
            return -1;
        }

        for(int index = 0; index < list.size(); index++) {
            int currentNumber = list.get(index);
            int diff = target - currentNumber;
            List<Integer> subList = list.subList(index + 1, list.size());
            int recursiveResult = recursiveFunction(subList, diff, nbElements - 1);
            if(recursiveResult >= 0) {
                return recursiveResult * currentNumber;
            }
        }
        return -1;
    }
}