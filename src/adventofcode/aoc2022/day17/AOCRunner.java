package adventofcode.aoc2022.day17;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

/**
 * AdventOfCode 2022 day 17's instructions are <a href="https://adventofcode.com/2022/day/17">here</a>
 */
public class AOCRunner implements AOC {
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            Printer.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
