package adventofcode;

import template.AOC;
import utilities.AOCFactory;
import utilities.Printer;

/**
 * Join AdventOfCode <a href="https://adventofcode.com">here</a>
 */
public class Launcher {
    private static final int YEAR = 2022;
    private static final int DAY = 16;
    public static void main(String[] args) {
        try {
            AOC aoc = AOCFactory.getAOC(YEAR, DAY);
            Printer.println("Solution " + YEAR + "/12/" + DAY);
            aoc.run(YEAR + "/aoc" + DAY + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
