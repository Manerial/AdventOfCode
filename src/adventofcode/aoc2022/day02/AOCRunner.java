package adventofcode.aoc2022.day02;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class AOCRunner implements AOC {
    private static final int VICTORY_POINT = 6;
    private static final int DRAW_POINT = 3;
    private static final int DEFEAT_POINT = 0;
    private int score;
    private int victory;
    private int defeat;
    private int draw;

    @Override
    public void run(String file) {
        try {
			// Inputs reading
            List<String> list = FileLoader.readListFromFile(file);
			// Set the score to 0
            resetScore();
            for (String item : list) {
                item = system1(item);
                getScore(item);
            }
            Printer.println("Solution 1 : " + (score + defeat * DEFEAT_POINT + draw * DRAW_POINT + victory * VICTORY_POINT));

            resetScore();
            for (String item : list) {
                item = system2(item);
                getScore(item);
            }
            Printer.println("Solution 2 : " + (score + defeat * DEFEAT_POINT + draw * DRAW_POINT + victory * VICTORY_POINT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetScore() {
        score = victory = defeat = draw = 0;
    }

    private void getScore(String item) {
		// Get the current play
        switch (item.split(" ")[1]) {
            case "C":
                score += 3;
                break;
            case "B":
                score += 2;
                break;
            case "A":
                score += 1;
                break;
            default:
                break;
        }
		// Get the correct case
        switch (item) {
            case "C C":
            case "B B":
            case "A A":
                draw++;
                break;
            case "B C":
            case "A B":
            case "C A":
                victory++;
                break;
            case "A C":
            case "C B":
            case "B A":
                defeat++;
                break;
            default:
                break;
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
