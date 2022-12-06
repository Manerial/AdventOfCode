package adventofcode.y2022.aoc4;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class AOC4 {
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            int contains = 0;
            int overlap = 0;
            for (String string : list) {
				// On récupère nos ranges
                Range range1 = getRange(string.split(",")[0]);
                Range range2 = getRange(string.split(",")[1]);
				// Si l'un contient l'autre, on incrémente
                if (range1.contains(range2) || range2.contains(range1)) {
                    contains++;
                }
				// Si l'un dépasse sur l'autre, on incrémente
                if (range1.isOverlappedBy(range2) || range2.isOverlappedBy(range1)) {
                    overlap++;
                }
            }
            Printer.println("Solution 1 : " + contains);
            Printer.println("Solution 2 : " + overlap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Range getRange(String str) {
        int borne1 = Integer.parseInt(str.split("-")[0]);
        int borne2 = Integer.parseInt(str.split("-")[1]);
        return new Range(borne1, borne2);
    }

}
