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
        double compareResult = root.compare();
        long min = 0;
        long max = Long.MAX_VALUE / 16; // Arbitrary choice. We suppose that the number is below 2^59
        while (compareResult != 0) {
            long median = (min + max) / 2;
            humn.setResult(median);
            compareResult = root.compare();

            if (compareResult > 0) {
                min = median;
            } else if (compareResult < 0) {
                max = median;
            }
        }
    }
}