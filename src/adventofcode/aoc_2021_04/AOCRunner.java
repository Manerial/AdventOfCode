package adventofcode.aoc_2021_04;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AOCRunner implements AOC {
    private final List<Bingo> grids = new ArrayList<>();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<String> plays = new ArrayList<>(Arrays.asList(list.get(0).split(",")));

            List<String> gridLines = new ArrayList<>();
            for (String str : list.subList(2, list.size())) {
                if (str.equals("")) {
                    grids.add(new Bingo(gridLines));
                    gridLines = new ArrayList<>();
                } else {
                    gridLines.add(str);
                }
            }
            grids.add(new Bingo(gridLines));

            play(plays);
            Printer.print("Solution 1 : ");
            printFirstWinner(plays);

            Collections.reverse(plays);
            Printer.print("Solution 2 : ");
            printFirstWinner(plays);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFirstWinner(List<String> plays) {
        for (String play : plays) {
            List<Bingo> winnerGrid = grids.stream().filter(bingo -> bingo.getWinPlay().equals(play)).collect(Collectors.toList());
            if (!winnerGrid.isEmpty()) {
                printGrid(winnerGrid.get(0));
                break;
            }
        }
    }

    private void printGrid(Bingo bingo) {
        int sum = bingo.getGridContentByCheck(false).stream().map(Integer::parseInt).reduce(Integer::sum).get();
        Printer.println(sum * Integer.parseInt(bingo.getWinPlay()));
    }

    private void play(List<String> plays) {
        for (String play : plays) {
            List<Bingo> notWinGrid = grids.stream().filter(bingo -> !bingo.isWin()).collect(Collectors.toList());
            for (Bingo grid : notWinGrid) {
                grid.play(play);
            }
        }
    }

}
