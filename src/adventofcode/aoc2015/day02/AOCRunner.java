package adventofcode.aoc2015.day02;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * AdventOfCode 2015 day 2's instructions are <a href="https://adventofcode.com/2015/day/2">here</a>
 */
public class AOCRunner implements AOC {
    private final List<Present> presents = new ArrayList<>();

    @Override
    public void run(String file) throws IOException {
        List<String> list = FileLoader.readListFromFile(file);
        for (String item : list) {
            String[] dimensions = item.split("x");
            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);
            Present present = new Present(length, width, height);
            presents.add(present);
        }
        Printer.println(presents.stream().map(Present::getPaper).reduce(Integer::sum).orElse(0));
    }
}