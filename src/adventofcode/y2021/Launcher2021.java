package adventofcode.y2021;

import adventofcode.y2021.ca1.CA1;
import utilities.Printer;

public class Launcher2021 {
    private static final String YEAR = "2021";
    public static void main(String[] args) {
        Printer.println("Solution jour 1");
        CA1.run(YEAR + "/ca1.txt");
    }
}
