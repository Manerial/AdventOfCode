package aoc.exercises.year2022.day09;

import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private final GridOfMoves grid2 = new GridOfMoves(2);
    private final GridOfMoves grid10 = new GridOfMoves(10);

    @Override
    public void run() {
        for (String item : inputList) {
            String direction = item.split(" ")[0];
            int move = Integer.parseInt(item.split(" ")[1]);

            grid2.move(direction, move);
            grid10.move(direction, move);
        }
        solution1 = grid2.countQueue();
        solution2 = grid10.countQueue();
    }
}
