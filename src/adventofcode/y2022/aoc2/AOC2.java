package adventofcode.y2022.aoc2;

import java.io.IOException;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class AOC2 {
    private static final int VICT_POINT = 6;
    private static final int DRAW_POINT = 3;
    private static final int DEFT_POINT = 0;
    private static int score;
    private static int vict;
    private static int deft;
    private static int draw;

    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            resetScore();
            for (String item : list) {
                item = systeme1(item);
                getScore(item);
            }
            Printer.println("Solution 1 : " + (score + deft * DEFT_POINT + draw * DRAW_POINT + vict * VICT_POINT));

            resetScore();
            for (String item : list) {
                item = systeme2(item);
                getScore(item);
            }
            Printer.println("Solution 2 : " + (score + deft * DEFT_POINT + draw * DRAW_POINT + vict * VICT_POINT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetScore() {
        score = vict = deft = draw = 0;
    }

    private static void getScore(String item) {
        switch (item.split(" ")[1]) {
            case "C":
                score += 1;
            case "B":
                score += 1;
            case "A":
                score += 1;
                break;
            default:
                break;
        }
        switch (item) {
            case "C C":
            case "B B":
            case "A A":
                draw++;
                break;
            case "B C":
            case "A B":
            case "C A":
                vict++;
                break;
            case "A C":
            case "C B":
            case "B A":
                deft++;
                break;
            default:
                break;
        }
    }

    // Solution 1
    private static String systeme1(String item) {
        item = item.replace("X", "A");
        item = item.replace("Y", "B");
        item = item.replace("Z", "C");
        return item;
    }

    // Solution 2
    private static String systeme2(String item) {
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
