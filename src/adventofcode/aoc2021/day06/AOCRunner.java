package adventofcode.aoc2021.day06;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.*;

/**
 * AdventOfCode 2021 day 6's instructions are <a href="https://adventofcode.com/2021/day/6">here</a>
 */
public class AOCRunner implements AOC {
    private final Map<Integer, Long> fishes = new HashMap<>();
    private final List<Long> newbornFishes = new ArrayList<>();

    @Override
    public void run(String file) {
        try {
            int days1 = 80;
            int days2 = 256;
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                Arrays.stream(item.split(",")).map(Integer::parseInt).forEach(fishDay -> fishes.compute(fishDay, (k, v) -> (v != null) ? v + 1 : 1));

                for (int day = 0; day < days1; day++) {
                    live(day);
                }
                Printer.println("Solution 1 : " + fishes.values().stream().reduce(Long::sum).orElse(0L));

                for (int day = days1; day < days2; day++) {
                    live(day);
                }
                Printer.println("Solution 2 : " + fishes.values().stream().reduce(Long::sum).orElse(0L));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
