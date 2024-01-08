package aoc.exercises.year2023.day02;

import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Game> games = inputParser.parseInput();
        Map<String, Integer> set = new HashMap<>();
        set.put("red", 12);
        set.put("green", 13);
        set.put("blue", 14);
        solution1 = games.stream().mapToInt(game -> game.getIdIfPossible(set)).sum();
        solution2 = games.stream()
                .mapToInt(game -> game.getMaxOfColor("red") * game.getMaxOfColor("green") * game.getMaxOfColor("blue"))
                .sum();
    }
}