package aoc.exercises.year2015.day10;

import utilities.AbstractAOC;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String input = inputList.get(0);
        for (int i = 0; i < 40; i++) {
            input = lookAndSay(input);
        }
        solution1 = input.length();

        for (int i = 0; i < 10; i++) {
            input = lookAndSay(input);
        }
        solution2 = input.length();
    }

    /**
     * <pre>
     * Build a look-and-say string by returning the string as if it was reading aloud.
     * 1 -> one 1 (11) -> two 1 (21) -> one 2 one 1 (1211), etc...
     * <a href="https://en.wikipedia.org/wiki/Look-and-say_sequence">Look-and-say sequence</a>
     * </pre>
     *
     * @param input : the string to look and say
     * @return the look-and-say string
     */
    private String lookAndSay(String input) {
        Pattern pattern = Pattern.compile("(1+|2+|3+)");
        Matcher matcher = pattern.matcher(input);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String group = matcher.group(1);
            builder.append(group.length()).append(group.charAt(0));
        }
        return builder.toString();
    }
}