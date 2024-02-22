package aoc.exercises.year2017.day09;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2017/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String line = inputList.get(0);
        String cleanCancel = cleanCancelCharacters(line);
        long numberOfGarbageChars = countGarbage(cleanCancel) * 2;
        String fullyClean = cleanGarbage(cleanCancel);
        solution1 = countGroups(fullyClean);
        solution2 = cleanCancel.length() - fullyClean.length() - numberOfGarbageChars;
    }

    private long countGarbage(String line) {
        return line.chars().filter(c -> c == '>').count();
    }

    private String cleanCancelCharacters(String line) {
        return line.replaceAll("!.", "");
    }

    private String cleanGarbage(String line) {
        return line.replaceAll("<[^>]*>", "");
    }

    private int countGroups(String line) {
        int count = 0;
        int groupIndex = 1;
        for (char c : line.toCharArray()) {
            if (c == '{') {
                count += groupIndex;
                groupIndex++;
            } else if (c == '}') {
                groupIndex--;
            }
        }
        return count;
    }
}