package adventofcode.aoc_2021_02;

import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

import template.AOC;

public class AOC_2021_02 extends AOC {

    @Override
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
