package adventofcode.aoc2022.day13;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONArray;
import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AdventOfCode 2022 day 13's instructions are <a href="https://adventofcode.com/2022/day/13">here</a>
 */
public class AOCRunner implements AOC {
    private final List<ImmutablePair<JSONArray, JSONArray>> pairPackets = new ArrayList<>();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            parseListInPackets(list);
            int sumOfIndex = getSumOfIndexSorted();
            Printer.println("Solution 1 : " + sumOfIndex);

            list.add("[[2]]");
            list.add("[[6]]");

            List<String> sortedList = sortByArray(list);
            Printer.println("Solution 2 : " + (sortedList.indexOf("[[2]]") + 1) * (sortedList.indexOf("[[6]]") + 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseListInPackets(List<String> list) {
        for (int index = 0; index < list.size(); index = index + 3) {
            JSONArray json1 = new JSONArray(list.get(index));
            JSONArray json2 = new JSONArray(list.get(index + 1));
            pairPackets.add(new ImmutablePair<>(json1, json2));
        }
    }

    private int getSumOfIndexSorted() {
        int index = 1;
        int sumOfIndex = 0;
        for (ImmutablePair<JSONArray, JSONArray> pair : pairPackets) {
            sumOfIndex += (JSONUtilities.compare(pair.getLeft(), pair.getRight()) <= 0) ? index : 0;
            index++;
        }
        return sumOfIndex;
    }

    private List<String> sortByArray(List<String> list) {
        return list.stream()
                .filter(s -> !s.isBlank())
                .sorted((o1, o2) -> {
                    JSONArray packet1 = new JSONArray(o1);
                    JSONArray packet2 = new JSONArray(o2);
                    return JSONUtilities.compare(packet1, packet2);
                })
                .collect(Collectors.toList());
    }
}
