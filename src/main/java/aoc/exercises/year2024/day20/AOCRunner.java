package aoc.exercises.year2024.day20;

import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/20">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private List<IndexedPosition> race;
    long raceSize;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        race = inputParser.parseInput();
        raceSize = race.size();
        int saving = isExample ? 20 : 100;
        solution1 = countShortcutSavingAtLeast(saving, 2);
        saving = isExample ? 74 : 100;
        solution2 = countShortcutSavingAtLeast(saving, 20);
    }

    private long countShortcutSavingAtLeast(int minSaving, int shortcutSize) {
        return race.stream()
                .mapToLong(position -> countCheatPaths(minSaving, shortcutSize, position))
                .sum();
    }

    private long countCheatPaths(int minSaving, int shortcutSize, IndexedPosition position) {
        return race.stream()
                .skip(minSaving + position.index())
                .filter(nextPosition -> nextPosition.position().getManhattanDistance(position.position()) <= shortcutSize)
                .filter(nextPosition -> raceSize - (position.index() + (raceSize - nextPosition.index()) + nextPosition.position().getManhattanDistance(position.position())) >= minSaving)
                .count();
    }
}