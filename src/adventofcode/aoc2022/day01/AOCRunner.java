package adventofcode.aoc2022.day01;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AdventOfCode 2022 day 1's instructions are <a href="https://adventofcode.com/2022/day/1">here</a>
 */
public class AOCRunner implements AOC {
    private List<Integer> backpacks = new ArrayList<>();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            createBackPacks(list);
            Collections.sort(backpacks);
            int most = backpacks.get(backpacks.size() - 1);
            int threeMost = backpacks.subList(backpacks.size() - 3, backpacks.size())
                    .stream()
                    .reduce(Integer::sum)
                    .orElse(0);
            Printer.println("Solution 1 : " + most);
            Printer.println("Solution 2 : " + threeMost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBackPacks(List<String> list) {
        String[] backpackStr = String.join("\r\n", list)
                .split("\r\n\r\n");

        backpacks = Stream.of(backpackStr)
                .map(fruit -> Stream.of(fruit.split("\r\n"))
                        .map(Integer::parseInt)
                        .reduce(Integer::sum)
                        .orElse(0)
                )
                .collect(Collectors.toList());
    }
}