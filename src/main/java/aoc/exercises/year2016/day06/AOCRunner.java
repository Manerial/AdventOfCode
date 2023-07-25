package aoc.exercises.year2016.day06;

import utilities.AbstractAOC;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/6">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    record RecordSolutions(String solution1, String solution2) {
    }

    @Override
    public void run() {
        RecordSolutions recordSolutions = decodeRepetitions();
        solution1 = recordSolutions.solution1;
        solution2 = recordSolutions.solution2;
    }

    private RecordSolutions decodeRepetitions() {
        StringBuilder mostCommon = new StringBuilder();
        StringBuilder leastCommon = new StringBuilder();
        List<String[]> charsList = inputList.stream()
                .map(s -> s.split(""))
                .toList();
        int length = charsList.get(0).length;
        for (int i = 0; i < length; i++) {
            Map<String, Long> charsFrequencies = getCharsFrequenciesAt(charsList, i);
            mostCommon.append(getMostFrequentChar(charsFrequencies));
            leastCommon.append(getLeastFrequentChar(charsFrequencies));
        }
        return new RecordSolutions(mostCommon.toString(), leastCommon.toString());
    }

    private String getMostFrequentChar(Map<String, Long> charsFrequencies) {
        return charsFrequencies
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElseThrow()
                .getKey();
    }

    private String getLeastFrequentChar(Map<String, Long> charsFrequencies) {
        return charsFrequencies
                .entrySet().stream()
                .min(Map.Entry.comparingByValue()).orElseThrow()
                .getKey();
    }

    private static Map<String, Long> getCharsFrequenciesAt(List<String[]> strings, int i) {
        return strings.stream()
                .map(stringArray -> stringArray[i])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
