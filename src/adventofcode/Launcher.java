package adventofcode;

import template.AOC;
import utilities.AOCFactory;
import utilities.Printer;

public class Launcher {
    private static final int YEAR = 2021;
    private static final int DAY = 3;
    public static void main(String[] args) {
    	AOC aoc = AOCFactory.getAOC(YEAR, DAY);
        Printer.println("Solution " + YEAR + "/12/" + DAY);
        aoc.run(YEAR + "/aoc" + DAY + ".txt");
    }
}
