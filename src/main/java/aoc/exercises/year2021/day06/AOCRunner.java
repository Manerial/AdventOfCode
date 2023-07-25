package aoc.exercises.year2021.day06;

import utilities.AbstractAOC;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final Map<Integer, Long> fishes = new HashMap<>();
    private final List<Long> newbornFishes = new ArrayList<>();

    @Override
    public void run() {
        int days1 = 80;
        int days2 = 256;

        for (String item : inputList) {
            Arrays.stream(item.split(",")).map(Integer::parseInt).forEach(fishDay -> fishes.compute(fishDay, (k, v) -> (v != null) ? v + 1 : 1));

            for (int day = 0; day < days1; day++) {
                live(day);
            }
            solution1 = fishes.values().stream().reduce(Long::sum).orElse(0L);

            for (int day = days1; day < days2; day++) {
                live(day);
            }
            solution2 = fishes.values().stream().reduce(Long::sum).orElse(0L);
        }
    }

    private void live(int day) {
        int dayOfWeek = day % 7;
        long babiesCantProduct = (day - 2 > 0) ? newbornFishes.get(day - 2) : 0;
        long newFishes = (fishes.get(dayOfWeek) != null) ? fishes.get(dayOfWeek) - babiesCantProduct : 0;
        newbornFishes.add(newFishes);
        fishes.compute((day + 2) % 7, (k, v) -> (v != null) ? v + newFishes : newFishes);
    }

}
