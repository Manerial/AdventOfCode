package aoc.exercises.year2022.day02;

import utilities.AbstractAOC;
import utilities.errors.NotAcceptedValue;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/2">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final int VICTORY_POINT = 6;
    private static final int DRAW_POINT = 3;
    private static final int DEFEAT_POINT = 0;
    private int score;
    private int victory;
    private int defeat;
    private int draw;

    @Override
    public void run() {
        // Set the score to 0
        resetScore();
        for (String item : inputList) {
            item = system1(item);
            getScore(item);
        }
        solution1 = getScore();

        resetScore();
        for (String item : inputList) {
            item = system2(item);
            getScore(item);
        }
        solution2 = getScore();
    }

    private int getScore() {
        return score + defeat * DEFEAT_POINT + draw * DRAW_POINT + victory * VICTORY_POINT;
    }

    private void resetScore() {
        score = victory = defeat = draw = 0;
    }

    private void getScore(String item) {
        String elt = item.split(" ")[1];
        // Get the current play
        switch (elt) {
            case "C" -> score += 3;
            case "B" -> score += 2;
            case "A" -> score += 1;
            default -> throw new NotAcceptedValue(elt);
        }
        // Get the correct case
        switch (item) {
            case "C C", "B B", "A A" -> draw++;
            case "B C", "A B", "C A" -> victory++;
            case "A C", "C B", "B A" -> defeat++;
            default -> throw new NotAcceptedValue(item);
        }
    }

    // Solution 1
    private String system1(String item) {
        item = item.replace("X", "A");
        item = item.replace("Y", "B");
        item = item.replace("Z", "C");
        return item;
    }

    // Solution 2
    private String system2(String item) {
        if (item.contains("A")) {
            item = item.replace("X", "C");
            item = item.replace("Y", "A");
            item = item.replace("Z", "B");
        }
        if (item.contains("B")) {
            item = item.replace("X", "A");
            item = item.replace("Y", "B");
            item = item.replace("Z", "C");
        }
        if (item.contains("C")) {
            item = item.replace("X", "B");
            item = item.replace("Y", "C");
            item = item.replace("Z", "A");
        }
        return item;
    }
}
