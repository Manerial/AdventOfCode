package adventofcode.aoc_2022_04;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;
import utilities.Range;

import java.io.IOException;
import java.util.List;

public class AOCRunner implements AOC {

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            int contains = 0;
            int overlap = 0;
            for (String string : list) {
				// We get our ranges
                Range range1 = getRange(string.split(",")[0]);
                Range range2 = getRange(string.split(",")[1]);
				// If one contains the other, ++
                if (range1.contains(range2) || range2.contains(range1)) {
                    contains++;
                }
				// If one overlap the other, ++
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

    private Range getRange(String str) {
        int borne1 = Integer.parseInt(str.split("-")[0]);
        int borne2 = Integer.parseInt(str.split("-")[1]);
        return new Range(borne1, borne2);
    }

}
