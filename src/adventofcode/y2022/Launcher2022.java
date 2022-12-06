package adventofcode.y2022;

import adventofcode.y2022.aoc1.AOC1;
import adventofcode.y2022.aoc2.AOC2;
import adventofcode.y2022.aoc3.CA3;
import adventofcode.y2022.aoc4.AOC4;
import adventofcode.y2022.aoc5.AOC5;
import adventofcode.y2022.aoc6.AOC6;
import utilities.Printer;

public class Launcher2022 {
    private static final String YEAR = "2022";
    public static void main(String[] args) {
        Printer.println("Solution jour 1");
        AOC1.run(YEAR + "/ca1.txt");
        Printer.println("Solution jour 2");
        AOC2.run(YEAR + "/ca2.txt");
        Printer.println("Solution jour 3");
        CA3.run(YEAR + "/ca3.txt");
        Printer.println("Solution jour 4");
        AOC4.run(YEAR + "/ca4.txt");
        Printer.println("Solution jour 5");
        AOC5.run(YEAR + "/ca5_2.txt");
        Printer.println("Solution jour 6");
        AOC6.run(YEAR + "/ca6.txt");
    }
}
