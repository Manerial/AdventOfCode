package aoc.exercises.year2025.day05;

import org.apache.commons.lang3.tuple.*;
import utilities.*;

import java.util.*;

public class InputParser extends AbstractInputParser<Pair<List<LongRange>, List<Long>>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Pair<List<LongRange>, List<Long>> parseInput() {
        List<LongRange> ranges = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        int i = 0;
        for (; i < inputList.size(); i++) {
            if (inputList.get(i).isEmpty()) {
                break;
            }
            LongRange range = LongRange.toRange(inputList.get(i), "-");
            ranges.add(range);
        }
        i++;
        for (; i < inputList.size(); i++) {
            numbers.add(Long.parseLong(inputList.get(i)));
        }
        return Pair.of(ranges, numbers);
    }
}
