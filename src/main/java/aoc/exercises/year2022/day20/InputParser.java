package aoc.exercises.year2022.day20;

import aoc.common_objects.RoundDeque;
import lombok.Setter;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractInputParser;

import java.util.List;

public class InputParser extends AbstractInputParser<RoundDeque<Pair<Long, Integer>>> {
    @Setter
    private long decryptionKey = 1;

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public RoundDeque<Pair<Long, Integer>> parseInput() {
        RoundDeque<Pair<Long, Integer>> result = new RoundDeque<>();
        int start = 0;
        for (String line : inputList) {
            result.addLast(new MutablePair<>(Long.parseLong(line) * decryptionKey, start++));
        }
        return result;
    }
}
