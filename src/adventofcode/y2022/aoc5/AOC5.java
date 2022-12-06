package adventofcode.y2022.aoc5;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import utilities.FileLoader;
import utilities.Printer;

public class AOC5 {
    public static void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            List<String> pile = new LinkedList<>();
            pile.add("JHGMZNTF");
            pile.add("VWJ");
            pile.add("GVLJBTH");
            pile.add("BPJNCDVL");
            pile.add("FWSMPRG");
            pile.add("GHCFBNVM");
            pile.add("DHGMR");
            pile.add("HNMVZD");
            pile.add("GNFH");
            Printer.print("Solution 1 : ");
            moveAndPrint(list, pile, false);
            Printer.print("Solution 2 : ");
            moveAndPrint(list, pile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void moveAndPrint(List<String> list, List<String> pile, boolean stack) {
        List<String> copyOfPile = new LinkedList<>(pile);
        for (String item : list) {
            // move 6 from 4 to 3
            moveCrates(copyOfPile, item, stack);
        }

        for (String item : copyOfPile) {
            Printer.print(item.charAt(item.length() - 1));
        }
        Printer.println();
    }

    private static void moveCrates(List<String> pile, String item, boolean stack) {
        int move = Integer.parseInt(item.split(" ")[1]);
        int from = Integer.parseInt(item.split(" ")[3]) - 1; // index start at 0
        int to = Integer.parseInt(item.split(" ")[5]) - 1; // index start at 0

        String pileFrom = pile.get(from);
        String pileTo = pile.get(to);
        String toMove = pileFrom.substring(pileFrom.length() - move, pileFrom.length());
        if (!stack) {
            toMove = reverseString(toMove); // Move 1 by 1 reverse the string
        }
        pileTo += toMove;
        pileFrom = pileFrom.substring(0, pileFrom.length() - move);
        pile.set(from, pileFrom);
        pile.set(to, pileTo);
    }

    private static String reverseString(String str) {
        StringBuilder reverse = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            reverse.insert(0, ch);
        }
        return reverse.toString();
    }
}
