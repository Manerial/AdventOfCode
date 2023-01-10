package adventofcode.aoc2015.day01;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;

/**
 * AdventOfCode 2015 day 1's instructions are <a href="https://adventofcode.com/2015/day/1">here</a>
 */
public class AOCRunner implements AOC {

    @Override
    public void run(String file) throws IOException {
        String item = FileLoader.readListFromFile(file).get(0);

        long parenthesesOpen = item.chars().filter(value -> value == '(').count();
        long parenthesesClose = item.length() - parenthesesOpen;
        Printer.println(parenthesesOpen - parenthesesClose);

        int basementIndex = 1;
        int floor = 0;
        for (char c : item.toCharArray()) {
            floor += (c == '(') ? 1 : -1;
            if (floor == -1) {
                break;
            }
            basementIndex++;
        }
        Printer.println(basementIndex);
    }
}