package adventofcode.y2021.aoc2;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

import template.AOC;

public class AOC2 extends AOC {

    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                Printer.println(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
