package adventofcode.aoc_2021_04;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AOC_2021_04 implements AOC {
    private List<Bingo> grids = new ArrayList<>();
    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<String> plays = List.of(list.get(0).split(","));

            List<String> gridLines = new ArrayList<>();
            for(String str : list.subList(2, list.size())) {
                if(str.equals("")) {
                    grids.add(new Bingo(gridLines));
                    gridLines = new ArrayList<>();
                } else {
                    gridLines.add(str);
                }
            }
            grids.add(new Bingo(gridLines));

            Printer.println(grids);

            play(plays);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play(List<String> plays) {
        for(String play : plays) {
            for(Bingo grid : grids) {
                if(grid.playHasWin(play)) {
                    Printer.println(grid);
                    Printer.println(grid.getGridContentByCheck(false));
                    int sum = grid.getGridContentByCheck(false).stream().map(Integer::parseInt).reduce(Integer::sum).get();
                    Printer.println(sum * Integer.parseInt(play));
                    return;
                }
            }
        }
    }

}
