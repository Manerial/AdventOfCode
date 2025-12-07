package aoc.exercises.year2025.day07;

import aoc.common_objects.*;
import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Splitter> splitters = extractSplitters();
        TachyonBeam startTachyonBeam = new TachyonBeam(new Position(inputList.getFirst().indexOf('S'), 0), 1);

        List<TachyonBeam> lastTachyonBeams = shotBeam(startTachyonBeam, splitters);

        solution1 = splitters.stream()
                .filter(Splitter::isUsed)
                .count();
        solution2 = lastTachyonBeams.stream()
                .mapToLong(TachyonBeam::getDistinctPasts)
                .sum();
    }

    private List<Splitter> extractSplitters() {
        List<Splitter> splitters = new ArrayList<>();
        for (int y = 0; y < inputList.size(); y++) {
            String line = inputList.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '^') {
                    Splitter splitter = new Splitter(new Position(x, y));
                    splitters.add(splitter);
                }
            }
        }
        return splitters;
    }

    private List<TachyonBeam> shotBeam(TachyonBeam startTachyonBeam, List<Splitter> splitters) {
        List<TachyonBeam> currentTachyonBeams = new ArrayList<>();
        currentTachyonBeams.add(startTachyonBeam);
        for (int i = 0; i < inputList.size(); i++) {
            List<TachyonBeam> nextTachyonBeams = new ArrayList<>();
            for (TachyonBeam tachyonBeam : currentTachyonBeams) {
                generateNextBeam(splitters, tachyonBeam, nextTachyonBeams);
            }
            currentTachyonBeams = nextTachyonBeams;
        }
        return currentTachyonBeams;
    }

    private static void generateNextBeam(List<Splitter> splitters, TachyonBeam tachyonBeam, List<TachyonBeam> nextTachyonBeams) {
        TachyonBeam nextTachyonBeam = tachyonBeam.getNext();
        Splitter splitter = splitters.stream()
                .filter(currentSplitter -> currentSplitter.getPosition().equals(nextTachyonBeam.getPosition()))
                .findAny()
                .orElse(null);

        if (splitter != null) {
            splitter.updateList(nextTachyonBeams, nextTachyonBeam);
        } else {
            nextTachyonBeams.add(nextTachyonBeam);
        }
    }
}