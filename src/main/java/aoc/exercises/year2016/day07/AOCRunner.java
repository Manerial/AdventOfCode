package aoc.exercises.year2016.day07;

import utilities.AbstractAOC;


/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final String ABBA_REGEX = "(\\w)(?!\\1)(\\w)(\\2)(\\1)";
    private static final String ABA_BAB_REGEX = "(\\w)(?!\\1)(\\w)(\\1).* \\| .*(\\2)(\\1)(\\2)";

    @Override
    public void run() {
        solution1 = countTlsIP();
        solution2 = countSslIP();
    }

    private long countTlsIP() {
        String anyAbba = ".*" + ABBA_REGEX + ".*";
        String anyAbbaInBrackets = ".*\\[\\w*" + ABBA_REGEX + "\\w*].*";
        return inputList.stream()
                .filter(s -> s.matches(anyAbba))
                .filter(s -> !s.matches(anyAbbaInBrackets))
                .count();
    }

    private long countSslIP() {
        String anyAbaBab = ".*" + ABA_BAB_REGEX + ".*";
        return inputList.stream()
                .map(AOCRunner::toSimpleString)
                .filter(s -> s.matches(anyAbaBab))
                .count();
    }

    private static String toSimpleString(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        String[] split = s.split("[\\[\\]]");
        for(int i = 0; i < split.length; i++) {
            if(i%2 == 0) {
                sb.insert(0, split[i] + " ");
            } else {
                sb.append(split[i]).append(" ");
            }
        }
        return sb.toString();
    }
}
