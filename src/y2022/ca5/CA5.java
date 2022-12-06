package y2022.ca5;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import utilities.FileLoader;

public class CA5 {
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
            for (String item : list) {
                // move 6 from 4 to 3
                int move = Integer.parseInt(item.split(" ")[1]);
                int from = Integer.parseInt(item.split(" ")[3]) - 1; // index start at 0
                int to = Integer.parseInt(item.split(" ")[5]) - 1; // index start at 0

                String pileFrom = pile.get(from);
                String pileTo = pile.get(to);
                String toMove = pileFrom.substring(pileFrom.length() - move, pileFrom.length());
                toMove = reverseString(toMove); // Move 1 by 1 reverse the string
                pileTo += toMove;
                pileFrom = pileFrom.substring(0, pileFrom.length() - move);
                pile.set(from, pileFrom);
                pile.set(to, pileTo);
            }

            for (String item : pile) {
                System.out.print(item.charAt(item.length() - 1));
            }
            System.out.println();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String reverseString(String str) {
        String reverse = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            reverse = ch + reverse;
        }
        return reverse;
    }

}
