package aoc.exercises.year2024.day05;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        PageUpdater pageUpdater = inputParser.parseInput();
        solution1 = pageUpdater.getMatchingUpdates().stream()
                .mapToInt(PageUpdate::getMiddlePage)
                .sum();

        solution2 = pageUpdater.getNonMatchingUpdatesCorrected().stream()
                .mapToInt(PageUpdate::getMiddlePage)
                .sum();
    }
}