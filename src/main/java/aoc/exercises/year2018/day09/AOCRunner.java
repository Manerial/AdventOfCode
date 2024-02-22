package aoc.exercises.year2018.day09;

import aoc.common_objects.RoundDeque;
import utilities.AbstractAOC;

import java.util.Arrays;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private RoundDeque<Integer> marbles;
    private int players;
    private int lastMarble;
    private long[] playersScore;

    @Override
    public void run() {
        initGame();
        play();
        solution1 = Arrays.stream(playersScore).max().orElseThrow();
        lastMarble *= 100;
        reinitGame();
        play();
        solution2 = Arrays.stream(playersScore).max().orElseThrow();
    }

    private void initGame() {
        String[] input = inputList.get(0)
                .replace(" points", "")
                .split(" players; last marble is worth ");
        players = Integer.parseInt(input[0]);
        lastMarble = Integer.parseInt(input[1]);
        reinitGame();
    }

    private void reinitGame() {
        marbles = new RoundDeque<>();
        playersScore = new long[players];
        marbles.add(0);
    }


    private void play() {
        for (int i = 1; i <= lastMarble; i++) {
            if (i % 23 == 0) {
                marbles.rotate(-7);
                int player = i % players;
                playersScore[player] += i + marbles.removeFirst();
            } else {
                marbles.rotate(2);
                marbles.addFirst(i);
            }
        }
    }
}