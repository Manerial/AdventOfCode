package aoc.exercises.year2021.day04;

import utilities.AbstractAOC;
import utilities.errors.NoSuchElementInListException;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2021/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        InputParser.Result result = inputParser.parseInput();
        List<Integer> draws = result.draws();
        List<Bingo> grids = result.grids();

        grids.forEach(bingo -> bingo.playAll(draws));

        Bingo bingo = getFirstWinner(grids, draws);
        solution1 = result(bingo);

        // The last to win is the first if we reverse all draws
        Collections.reverse(draws);
        Bingo bingo2 = getFirstWinner(grids, draws);
        solution2 = result(bingo2);
    }

    private Bingo getFirstWinner(List<Bingo> grids, List<Integer> draws) {
        for (int drawnNumber : draws) {
            List<Bingo> winnerGrid = grids.stream()
                    .filter(bingo -> bingo.getWinPlay().equals(drawnNumber))
                    .toList();
            if (!winnerGrid.isEmpty()) {
                return winnerGrid.get(0);
            }
        }
        throw new NoSuchElementInListException();
    }

    private int result(Bingo bingo) {
        int sum = bingo.getNotDrawnContent().stream()
                .mapToInt(Integer::intValue)
                .sum();
        return sum * bingo.getWinPlay();
    }
}
