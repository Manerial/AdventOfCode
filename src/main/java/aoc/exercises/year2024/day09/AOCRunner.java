package aoc.exercises.year2024.day09;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    int lastFileIndex;
    int firstEmptyMemIndex;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);

        Map<Integer, MemoryElement> memoryMap = inputParser.parseInput();
        sortByTheEnd(memoryMap);
        solution1 = getChecksum(memoryMap);

        Map<Integer, MemoryElement> memoryMap2 = inputParser.parseInput();
        sortByBlocs(memoryMap2);
        solution2 = getChecksum(memoryMap2);
    }

    private void sortByTheEnd(Map<Integer, MemoryElement> memoryMap) {
        int lastIndex = memoryMap.size() - 1;
        int currentIndex = 0;
        while (!checkSorted(memoryMap, currentIndex)) {
            if (memoryMap.get(currentIndex).getId() > -1) {
                currentIndex++;
                continue;
            }
            while (memoryMap.get(lastIndex).getId() == -1) {
                lastIndex--;
            }
            swap(memoryMap, currentIndex, lastIndex);
            currentIndex++;
        }
    }

    private void sortByBlocs(Map<Integer, MemoryElement> memoryMap) {
        int lastFileId = memoryMap.get(memoryMap.size() - 1).getId();
        lastFileIndex = memoryMap.size() - 1;
        for (int currentFileId = lastFileId; currentFileId >= 0; currentFileId--) {
            saveEmptyMemIndex(memoryMap);
            List<Pair<Integer, MemoryElement>> file = getFile(memoryMap, currentFileId);
            int indexEmptyMem = findIndexOfFittingEmptyMemory(memoryMap, file.size(), file.get(0).getKey());
            if (indexEmptyMem >= 0) {
                swapFileWithEmptyMem(memoryMap, indexEmptyMem, file);
            }
        }
    }

    private void swapFileWithEmptyMem(Map<Integer, MemoryElement> memoryMap, int indexEmptyMem, List<Pair<Integer, MemoryElement>> file) {
        for (int i = indexEmptyMem; i < indexEmptyMem + file.size(); i++) {
            swap(memoryMap, i, file.get(i - indexEmptyMem).getKey());
        }
    }

    private List<Pair<Integer, MemoryElement>> getFile(Map<Integer, MemoryElement> memoryMap, int fileId) {
        int index = lastFileIndex;
        while (true) {
            if (memoryMap.get(index).getId() == fileId) {
                return getFileFromIndex(memoryMap, index);
            }
            index--;
        }
    }

    private List<Pair<Integer, MemoryElement>> getFileFromIndex(Map<Integer, MemoryElement> memoryMap, int endFileIndex) {
        List<Pair<Integer, MemoryElement>> file = new ArrayList<>();
        MemoryElement lastMemoryElement = memoryMap.get(endFileIndex);
        lastFileIndex = lastMemoryElement.getStartFileIndex();
        for (int fileIndex = lastMemoryElement.getStartFileIndex(); fileIndex <= endFileIndex; fileIndex++) {
            file.add(new ImmutablePair<>(fileIndex, memoryMap.get(fileIndex)));
        }
        return file;
    }

    private int findIndexOfFittingEmptyMemory(Map<Integer, MemoryElement> memoryMap, int size, int fileIndex) {
        int emptyMemorySize = 0;
        for (int index = firstEmptyMemIndex; index <= fileIndex; index++) {
            if (memoryMap.get(index).getId() == -1) {
                emptyMemorySize++;
                if (emptyMemorySize == size) {
                    return index + 1 - size;
                }
            } else {
                emptyMemorySize = 0;
            }
        }
        return -1;
    }

    private void saveEmptyMemIndex(Map<Integer, MemoryElement> memoryMap) {
        while (memoryMap.get(firstEmptyMemIndex).getId() == -1) {
            firstEmptyMemIndex++;
        }
    }

    private boolean checkSorted(Map<Integer, MemoryElement> memoryMap, int currentIndex) {
        for (int i = currentIndex; i < memoryMap.size() - 1; i++) {
            if (memoryMap.get(i).getId() > 0) {
                return false;
            }
        }
        return true;
    }

    private void swap(Map<Integer, MemoryElement> memoryMap, int indexA, int indexB) {
        MemoryElement temp = memoryMap.get(indexA);
        memoryMap.put(indexA, memoryMap.get(indexB));
        memoryMap.put(indexB, temp);
    }

    private long getChecksum(Map<Integer, MemoryElement> memoryMap) {
        long result = 0L;
        for (int i = 0; i < memoryMap.size() - 1; i++) {
            long value = memoryMap.get(i).getMinId();
            result += value * i;
        }
        return result;
    }
}