package adventofcode.y2022;

import adventofcode.y2022.ca1.CA1;
import adventofcode.y2022.ca2.CA2;
import adventofcode.y2022.ca3.CA3;
import adventofcode.y2022.ca4.CA4;
import adventofcode.y2022.ca5.CA5;
import adventofcode.y2022.ca6.CA6;
import utilities.Printer;

public class Launcher2022 {
    private static final String YEAR = "2022";
    public static void main(String[] args) {
        Printer.println("Solution jour 1");
        CA1.run(YEAR + "/ca1.txt");
        Printer.println("Solution jour 2");
        CA2.run(YEAR + "/ca2.txt");
        Printer.println("Solution jour 3");
        CA3.run(YEAR + "/ca3.txt");
        Printer.println("Solution jour 4");
        CA4.run(YEAR + "/ca4.txt");
        Printer.println("Solution jour 5");
        CA5.run(YEAR + "/ca5_2.txt");
        Printer.println("Solution jour 6");
        CA6.run(YEAR + "/ca6.txt");
    }
}
