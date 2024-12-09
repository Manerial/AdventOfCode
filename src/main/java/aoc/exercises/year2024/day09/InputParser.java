package aoc.exercises.year2024.day09;

import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<Integer, MemoryElement>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Integer, MemoryElement> parseInput() {
        Map<Integer, MemoryElement> memoryMap = new HashMap<>();
        int id = 0;
        for (char c : inputList.get(0).toCharArray()) {
            int value = Character.getNumericValue(c);
            int trueId = (id % 2 == 0) ? id / 2 : -1;
            int startFileIndex = memoryMap.size();
            for (int i = 0; i < value; i++) {
                memoryMap.put(memoryMap.size(), new MemoryElement(trueId, startFileIndex));
            }
            id++;
        }
        return memoryMap;
    }
}
