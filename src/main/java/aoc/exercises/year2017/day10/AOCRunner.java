package aoc.exercises.year2017.day10;

import aoc.common_objects.RoundDeque;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private RoundDeque<Integer> numbers;
    private List<Integer> lengths;
    private int skipSize;
    private int totalSkips;

    @Override
    public void run() {
        init();
        knotHash();
        solution1 = getResultPart1();
        initPart2();
        for (int i = 0; i < 64; i++) {
            knotHash();
        }
        solution2 = getResultPart2();
    }

    private void init() {
        skipSize = 0;
        totalSkips = 0;
        numbers = new RoundDeque<>();
        lengths = new ArrayList<>();

        for (int i = 0; i < 256; i++) {
            numbers.add(i);
        }

        for (String item : inputList.get(0).split(",")) {
            lengths.add(Integer.parseInt(item));
        }
    }

    private void initPart2() {
        init();
        lengths = new ArrayList<>();
        for (int item : inputList.get(0).toCharArray()) {
            lengths.add(item);
        }
        lengths.addAll(List.of(17, 31, 73, 47, 23));
    }

    /**
     * Hash the numbers in the list using the algorithm described in the README.md
     * (reverse sublist of N elements in our numbers list, with N in our list of lengths)
     */
    private void knotHash() {
        for (int length : lengths) {
            List<Integer> tempList = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                tempList.add(numbers.removeFirst());
            }
            // by using addFirst, we reverse the order of tempList
            for (int i = 0; i < length; i++) {
                numbers.addFirst(tempList.get(i));
            }
            numbers.rotate((length + skipSize));
            totalSkips += length + skipSize;
            skipSize++;
        }
    }

    private int getResultPart1() {
        resetPosition();
        int first = numbers.getFirst();
        numbers.rotate(1);
        int second = numbers.getFirst();
        numbers.rotate(-1);
        return first * second;
    }

    private String getResultPart2() {
        resetPosition();
        List<List<Integer>> packsOf16 = splitInPacksOf16();
        List<Integer> denseHashs = getDenseHashs(packsOf16);
        return toHexaString(denseHashs);
    }

    /**
     * Reset the list to it's first position
     */
    private void resetPosition() {
        totalSkips %= numbers.size();
        numbers.rotate(-totalSkips);
    }

    /**
     * Split our list of numbers in packs of 16 integers
     *
     * @return A list of packs (type list) of 16 integers
     */
    private List<List<Integer>> splitInPacksOf16() {
        List<List<Integer>> packsOf16 = new ArrayList<>();
        while (!numbers.isEmpty()) {
            List<Integer> packOf16 = new ArrayList<>();
            for (int j = 0; j < 16; j++) {
                packOf16.add(numbers.removeFirst());
            }
            packsOf16.add(packOf16);
        }
        return packsOf16;
    }

    /**
     * Apply a XOR operation between the 16 elements of each pack
     *
     * @param packsOf16 A list of packs (type list) of 16 integers
     * @return A list of 16 integers, the result of the XOR operation
     */
    private List<Integer> getDenseHashs(List<List<Integer>> packsOf16) {
        List<Integer> denseHashs = new ArrayList<>();
        for (List<Integer> packOf16 : packsOf16) {
            int denseHash = packOf16.get(0);
            for (int i = 1; i < packOf16.size(); i++) {
                denseHash ^= packOf16.get(i);
            }
            denseHashs.add(denseHash);
        }
        return denseHashs;
    }

    /**
     * Convert the list of 16 integers into a string of hexadecimal digits
     *
     * @param denseHashs : A list of 16 integers from 0 to 255
     * @return A string of hexadecimal digits
     */
    private String toHexaString(List<Integer> denseHashs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer denseHash : denseHashs) {
            stringBuilder.append(String.format("%02x", denseHash));
        }
        return stringBuilder.toString();
    }

}