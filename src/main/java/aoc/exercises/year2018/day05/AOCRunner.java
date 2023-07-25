package aoc.exercises.year2018.day05;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final String REGEX_LETTER_MATCH_DIFFERENT_CASE = "(\\p{Ll})(?=\\p{Lu})(?i:\\1)|(\\p{Lu})(?=\\p{Ll})(?i:\\2)";
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Override
    public void run() {
        String reactivePolymer = inputList.get(0);
        solution1 = recursiveReaction(reactivePolymer).length();

        List<String> allPolymerBySuppression = getAllPolymerBySuppression(reactivePolymer);
        solution2 = allPolymerBySuppression.stream()
                .mapToInt(String::length)
                .min()
                .orElseThrow(NoSuchElementException::new);
    }

    private List<String> getAllPolymerBySuppression(String reactivePolymer) {
        List<String> allPolymerBySuppression = new ArrayList<>();
        for (char c : ALPHABET) {
            String regex = "[" + c + Character.toUpperCase(c) + "]";
            String newReactivePolymer = reactivePolymer.replaceAll(regex, "");
            allPolymerBySuppression.add(recursiveReaction(newReactivePolymer));
        }
        return allPolymerBySuppression;
    }

    private String recursiveReaction(String reactivePolymer) {
        String newReactivePolymer = reactivePolymer.replaceAll(REGEX_LETTER_MATCH_DIFFERENT_CASE, "");
        if (newReactivePolymer.length() == reactivePolymer.length()) {
            return newReactivePolymer;
        } else if (reactivePolymer.length() - newReactivePolymer.length() == 2) {
            return fastReact(newReactivePolymer);
        }
        return recursiveReaction(newReactivePolymer);
    }

    private String fastReact(String reactivePolymer) {
        String[] splitPolymer = reactivePolymer.split(REGEX_LETTER_MATCH_DIFFERENT_CASE);
        if (splitPolymer.length == 1) {
            return reactivePolymer;
        }
        String firstPart = splitPolymer[0];
        char lastCharFirstPart = getLastChar(firstPart);
        String lastPart = splitPolymer[1];
        char firstCharLastPart = getFirstChar(lastPart);

        while (Character.toUpperCase(lastCharFirstPart) == Character.toUpperCase(firstCharLastPart)) {
            firstPart = firstPart.substring(0, firstPart.length() - 1);
            lastPart = lastPart.substring(1);
            lastCharFirstPart = getLastChar(firstPart);
            firstCharLastPart = getFirstChar(lastPart);
        }
        return firstPart + lastPart;
    }

    private static char getLastChar(String str) {
        return str.charAt(str.length() - 1);
    }

    private static char getFirstChar(String str) {
        return str.charAt(0);
    }
}