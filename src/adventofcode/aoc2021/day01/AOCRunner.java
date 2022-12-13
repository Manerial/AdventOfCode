package adventofcode.aoc2021.day01;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AOCRunner implements AOC {

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            int count = countGreaterMeasure(list, 1);
            Printer.println("Solution 1 : " + count);
            int countGlide = countGreaterMeasure(list, 3);
            Printer.println("Solution 2 : " + countGlide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countGreaterMeasure(List<String> list, int glide) {
        int count = 0;
        int last = 0;
        List<Integer> glider = new ArrayList<>();
        for (String item : list) {
            glider.add(Integer.parseInt(item));
            if(glider.size() > glide) {
                glider.remove(0);
                int current = glider.stream().reduce(0, Integer::sum);
                if(last < current) {
                    count++;
                }
                last = current;
            }
        }
        return count;
    }
}
