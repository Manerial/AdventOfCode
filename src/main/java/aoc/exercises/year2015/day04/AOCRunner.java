package aoc.exercises.year2015.day04;

import org.apache.commons.codec.digest.DigestUtils;
import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/4">here</a>
 * </pre>
 */
// Can't be optimized
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String input = inputList.get(0);
        solution1 = findPasswordForLeading(input, "00000");
        solution2 = findPasswordForLeading(input, "000000");
    }

    /**
     * Test all combinations of input + password to find the first one for which the hash starts with the given leading.
     *
     * @param input   : the input to use
     * @param leading : the first characters to find in the hash
     * @return the first password that matches the conditions
     */
    private static int findPasswordForLeading(String input, String leading) {
        int password = 0;
        String output = "";
        while (!output.startsWith(leading)) {
            password++;
            output = DigestUtils.md5Hex(input + password);
        }
        return password;
    }
}