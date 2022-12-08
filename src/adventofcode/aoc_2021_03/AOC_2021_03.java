package adventofcode.aoc_2021_03;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AOC_2021_03 extends AOC {
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<Integer> binaryAverage = initList(list.get(0).length(), 0);
            extractBinary(list, binaryAverage);

            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            for(Integer integer : binaryAverage) {
                if(integer > list.size()/2) {
                    stringBuilder1.append("1");
                    stringBuilder2.append("0");
                } else {
                    stringBuilder1.append("0");
                    stringBuilder2.append("1");
                }
            }

            Printer.println(Integer.parseInt(stringBuilder1.toString(), 2) * Integer.parseInt(stringBuilder2.toString(), 2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> initList(int size, int defaultVal) {
        List<Integer> binaryAverage = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            binaryAverage.add(defaultVal);
        }
        return binaryAverage;
    }

    private void extractBinary(List<String> list, List<Integer> binaryAverage) {
        for(String string : list) {
            int incr = 0;
            for(char c : string.toCharArray()) {
                if(c == '0') {
                    binaryAverage.set(incr, binaryAverage.get(incr) + 1);
                }
                incr++;
            }
        }
    }
}
