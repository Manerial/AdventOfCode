package aoc.exercises.year2022.day21;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/21">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Monkey> monkeys = new ArrayList<>();
        for (String line : inputList) {
            monkeys.add(new Monkey(line));
        }
        monkeys.forEach(monkey -> monkey.setMonkeys(monkeys));

        Monkey root = monkeys.stream()
                .filter(monkey -> monkey.getName().equals("root"))
                .findAny()
                .orElseThrow();
        solution1 = Math.round(root.computeResult());

        Monkey humn = monkeys.stream()
                .filter(monkey -> monkey.getName().equals("humn"))
                .findAny()
                .orElseThrow();

        dichotomySearch(root, humn);
        solution2 = Math.round(humn.computeResult());
    }

    private static void dichotomySearch(Monkey root, Monkey humn) {
        boolean ascending = isAscending(root, humn);
        double compareResult = root.compare();
        long min = 0;
        long max = Long.MAX_VALUE;
        while (compareResult != 0) {
            long median = (min + max) / 2;
            humn.setResult(median);
            compareResult = root.compare();

            if (compareResult > 0) {
                if (!ascending) {
                    min = median;
                } else {
                    max = median;
                }
            } else if (compareResult < 0) {
                if (!ascending) {
                    max = median;
                } else {
                    min = median;
                }
            }
        }
    }

    private static boolean isAscending(Monkey root, Monkey humn) {
        humn.setResult(0L);
        double firstResult = root.compare();
        humn.setResult(1L);
        return firstResult < root.compare();
    }
}