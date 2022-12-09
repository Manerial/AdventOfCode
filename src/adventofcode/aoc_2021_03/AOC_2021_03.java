package adventofcode.aoc_2021_03;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AOC_2021_03 implements AOC {
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<Integer> binaryAverage = initList(list.get(0).length(), 0);
            extractBinaryCount(list, binaryAverage);

            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Integer integer : binaryAverage) {
                if (integer > list.size() / 2) {
                    stringBuilder1.append("1");
                    stringBuilder2.append("0");
                } else {
                    stringBuilder1.append("0");
                    stringBuilder2.append("1");
                }
            }
            int gammaRate = Integer.parseInt(stringBuilder1.toString(), 2);
            int epsilonRate = Integer.parseInt(stringBuilder2.toString(), 2);

            Printer.println("Solution 1 : " + (gammaRate * epsilonRate));

            String binaryO2Rate = extractBinaryCountFiltered(list, 0,  true);
            String binaryCO2Rate = extractBinaryCountFiltered(list, 0, false);

            int co2Rate = Integer.parseInt(binaryCO2Rate, 2);
            int o2Rate = Integer.parseInt(binaryO2Rate, 2);

            Printer.println(binaryO2Rate + " " + binaryCO2Rate);

            Printer.println("Solution 2 : " + (o2Rate * co2Rate));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> initList(int size, int defaultVal) {
        List<Integer> binaryAverage = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            binaryAverage.add(defaultVal);
        }
        return binaryAverage;
    }

    private void extractBinaryCount(List<String> list, List<Integer> binaryAverage) {
        for (String item : list) {
            int incr = 0;
            for (char c : item.toCharArray()) {
                extractBinary(c, binaryAverage, incr);
                incr++;
            }
        }
    }

    private void extractBinary(char item, List<Integer> binaryAverage, int position) {
        if (item == '0') {
            binaryAverage.set(position, binaryAverage.get(position) + 1);
        }
    }

    private String extractBinaryCountFiltered(List<String> list, int index, boolean isO2) {
        int finalIndex = index;
        long count1 = list.stream().filter(e -> e.charAt(finalIndex) == '1').count();
        long count2 = list.size() - count1;
        char nextChar;
        if (count1 >= count2) {
            nextChar = (isO2) ? '1' : '0';
        } else {
            nextChar = (isO2) ? '0' : '1';
        }
        index++;
        List<String> filteredList = list.stream().filter(e -> e.charAt(finalIndex) == nextChar).collect(Collectors.toList());
        return  filteredList.size() > 1 ? extractBinaryCountFiltered(filteredList, index, isO2) : filteredList.get(0);
    }
}
