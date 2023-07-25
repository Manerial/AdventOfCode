package aoc.exercises.year2018.day02;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int countDouble = 0;
        int countTriple = 0;
        for (String item : inputList) {
            countDouble += hasAnyLetterTimes(item, 2);
            countTriple += hasAnyLetterTimes(item, 3);
        }

        solution1 = countDouble * countTriple;

        solution2 = getSimilarLine(inputList);
    }

    private int hasAnyLetterTimes(String item, int times) {
        for (char c : item.toCharArray()) {
            long count = item.chars().filter(value -> value == c).count();
            if (count == times) {
                return 1;
            }
        }
        return 0;
    }

    private String getSimilarLine(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                String string1 = list.get(i);
                String string2 = list.get(j);
                String similar = getSimilar(string1.toCharArray(), string2.toCharArray());
                if (similar.length() == string1.length() - 1) {
                    return similar;
                }
            }
        }
        return null;
    }

    private String getSimilar(char[] chars1, char[] chars2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars1.length; i++) {
            if (chars1[i] == chars2[i]) {
                sb.append(chars1[i]);
            }
        }
        return sb.toString();
    }
}