package aoc.exercises.year2020.day05;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<Integer> seatIds = new ArrayList<>(parseInput());

        solution1 = Collections.max(seatIds);

        int nbOfSeats = seatIds.size() + 1;
        int firstSeatId = Collections.min(seatIds);
        int sumOfAllSeatsId = ((nbOfSeats) * (nbOfSeats - 1)) / 2 + (firstSeatId * nbOfSeats);
        int sumOfAssignedSeatsId = seatIds.stream().mapToInt(Integer::intValue).sum();

        solution2 = sumOfAllSeatsId - sumOfAssignedSeatsId;
    }

    private List<Integer> parseInput() {
        return inputList.stream()
                .map(PlanesSeat::new)
                .map(PlanesSeat::getSeat)
                .toList();
    }
}