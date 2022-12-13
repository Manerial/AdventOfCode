package adventofcode.aoc2022.day09;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class AOCRunner implements AOC {
    private final GridOfMoves grid2 = new GridOfMoves(2);
    private final GridOfMoves grid10 = new GridOfMoves(10);
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                String direction = item.split(" ")[0];
                int move = Integer.parseInt(item.split(" ")[1]);

                grid2.move(direction, move);
                grid10.move(direction, move);
            }
            Printer.println("Solution 1 : " + grid2.countQueue());
            Printer.println("Solution 2 : " + grid10.countQueue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
