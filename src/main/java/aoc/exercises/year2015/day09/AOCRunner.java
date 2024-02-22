package aoc.exercises.year2015.day09;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2015/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Town> towns = inputParser.parseInput();

        List<Integer> allPathsDistances = towns.stream()
                .map(Town::getAllPaths)
                .flatMap(List::stream)
                .map(Path::computeDistance)
                .toList();
        solution1 = allPathsDistances.stream()
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow();
        solution2 = allPathsDistances.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
    }
}