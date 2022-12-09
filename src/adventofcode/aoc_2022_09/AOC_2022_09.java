package adventofcode.aoc_2022_09;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.List;

public class AOC_2022_09 implements AOC {
    private GridOfMoves grid = new GridOfMoves(10);
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                String direction = item.split(" ")[0];
                int move = Integer.parseInt(item.split(" ")[1]);

                grid.move(direction, move);
            }
            Printer.println(grid.countQueue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
