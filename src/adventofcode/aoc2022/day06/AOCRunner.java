package adventofcode.aoc2022.day06;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

/**
 * AdventOfCode 2022 day 6's instructions are <a href="https://adventofcode.com/2022/day/6">here</a>
 */
public class AOCRunner implements AOC {

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                Printer.print("Solution 1 : ");
                findFirstPacket(item, 4);
                Printer.print("Solution 2 : ");
                findFirstPacket(item, 14);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findFirstPacket(String item, int size) {
        int incr = 0;
        boolean found = false;
        while (!found && incr < item.length() - size) {
            String subStr = item.substring(incr, incr + size);
            long count = subStr.chars().distinct().count();
            if (count == size) {
                Printer.println(incr + size);
                found = true;
            }
            incr++;
        }
    }
}
