package aoc.exercises.year2020.day03;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> listTreesOnTheWay = new ArrayList<>();
        listTreesOnTheWay.add(countTreesOnWay(1, 1));
        listTreesOnTheWay.add(countTreesOnWay(1, 3));
        listTreesOnTheWay.add(countTreesOnWay(1, 5));
        listTreesOnTheWay.add(countTreesOnWay(1, 7));
        listTreesOnTheWay.add(countTreesOnWay(2, 1));

        solution1 = listTreesOnTheWay.get(1);
        solution2 = listTreesOnTheWay.stream()
                .mapToLong(Integer::longValue)
                .reduce(1L, Math::multiplyExact);
    }

    public int countTreesOnWay(int down, int right) {
        int charIndex = 0;
        int trees = 0;

        for (int currentLineIndex = 0; currentLineIndex < inputList.size(); currentLineIndex += down) {
            String inputLine = inputList.get(currentLineIndex);
            char currentChar = inputLine.charAt(charIndex % inputLine.length());
            trees += (currentChar == '#') ? 1 : 0;
            charIndex += right;
        }

        return trees;
    }
}