package aoc.exercises.year2017.day04;

import utilities.AbstractAOC;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    @Override
    public void run() {
        solution1 = getNbValidPassword(false);
        solution2 = getNbValidPassword(true);
    }

    private int getNbValidPassword(boolean checkAnagrams) {
        int countValidPassword = 0;
        for(String line : inputList) {
            String[] words = line.split(" ");
            if(checkAnagrams) {
                words = sortLettersForEachWord(words);
            }
            if(isValid(words)) {
                countValidPassword++;
            }
        }
        return countValidPassword;
    }

    private String[] sortLettersForEachWord(String[] words) {
        List<String> newWords = new ArrayList<>();
        for(String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            newWords.add(new String(chars));
        }
        return newWords.toArray(new String[0]);
    }

    private boolean isValid(String[] words) {
        Set<String> set = new HashSet<>(List.of(words));
        return words.length == set.size();
    }
}