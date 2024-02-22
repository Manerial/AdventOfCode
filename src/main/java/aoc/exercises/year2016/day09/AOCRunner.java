package aoc.exercises.year2016.day09;

import lombok.extern.slf4j.Slf4j;
import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/9">here</a>
 * </pre>
 */
@Slf4j
public class AOCRunner extends AbstractAOC {


    @Override
    public void run() {
        solution1 = getDecompressedSize(inputList.get(0), true);
        solution2 = getDecompressedSize(inputList.get(0), false);
    }

    private long getDecompressedSize(String string, boolean once) {
        long size = 0;

        // X(8x2)(3x3)ABCY
        String[] split = string.split("\\(", 2); // ["X", "8x2)(3x3)ABCY"]
        size += split[0].length(); // X

        if (split.length == 1) {
            return size;
        }

        String part2 = split[1]; // 8x2)(3x3)ABCY

        String[] split2 = part2.split("\\)", 2); // ["8x2", "(3x3)ABCY"]

        String[] split3 = split2[0].split("x"); // ["8", "2"]
        int numberChars = Integer.parseInt(split3[0]);
        long times = Long.parseLong(split3[1]);

        String subString1 = split2[1].substring(0, numberChars); // (3x3)ABC
        String subString2 = split2[1].substring(numberChars); // Y

        if (once) {
            size += subString1.length() * times;
        } else {
            size += getDecompressedSize(subString1, once) * times;
        }
        size += getDecompressedSize(subString2, once);

        return size;
    }
}