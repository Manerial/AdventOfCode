package adventofcode.aoc_2022_10;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class AOCRunner implements AOC {
    private Integer tick = 0;
    private Integer x = 1;
    private Integer sum = 0;
    private Integer spritePosition = 1;
    private String screen = "";

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                String instruction = item.split(" ")[0];
                switch (instruction) {
                    case "noop":
                        times(0);
                        break;
                    case "addx":
                        int addx = Integer.parseInt(item.split(" ")[1]);
                        times(0);
                        times(addx);
                        break;
                    default:
                        break;
                }
            }
            Printer.println("Solution 1 : " + sum);
            Printer.println("Solution 2 :\r\n" + screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void times(int spriteMove) {
        tick++;
        testScreen(spriteMove);
        printSprite();
        spritePosition += spriteMove;
    }

    private void testScreen(int i) {
        if ((tick + 20) % 40 == 0) {
            sum += tick * x;
        }
        x += i;
    }

    private void printSprite() {
        if (tick % 40 == 0 && spritePosition < 38) {
            screen += " ";
        } else if (tick % 40 >= spritePosition && tick % 40 <= spritePosition + 2) {
            screen += "#";
        } else {
            screen += " ";
        }
        if (tick % 40 == 0) {
            screen += "\r\n";
        }
    }
}
