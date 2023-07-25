package aoc.exercises.year2022.day05;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/5">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        List<String> pile = parsePile(inputList.subList(0, inputList.indexOf("")));
        List<String> moves = inputList.subList(inputList.indexOf("") + 1, inputList.size());

        solution1 = move(moves, pile, false);
        solution2 = move(moves, pile, true);
    }

    /**
     * <pre>
     * Parse a list with the following shape :
     * [A] [B] ...
     * [C] [D] [E] ...
     * </pre>
     *
     * @param list : The list to parse
     * @return a new list containing values as CA, DB, E
     */
    private List<String> parsePile(List<String> list) {
        Pattern pattern = Pattern.compile("\\[(.)]");
        String splitIn4 = "(?<=\\G.{4})";
        int numberOfPiles = list.get(0).split(splitIn4).length;
        List<String> pile = new ArrayList<>();
        for (int i = 0; i < numberOfPiles; i++) {
            pile.add("");
        }

        for (String item : list) {
            String[] packets = item.split(splitIn4);
            for (int i = 0; i < packets.length; i++) {
                Matcher matcher = pattern.matcher(packets[i]);
                if (matcher.find()) {
                    pile.set(i, matcher.group(1) + pile.get(i));
                }
            }
        }
        return pile;
    }

    private String move(List<String> moves, List<String> pile, boolean stack) {
        List<String> copyOfPile = new ArrayList<>(pile);
        for (String move : moves) {
            moveCrates(copyOfPile, move, stack);
        }

        return copyOfPile.stream()
                .reduce("", (partialString, element) -> partialString + element.charAt(element.length() - 1));
    }

    private void moveCrates(List<String> pile, String move, boolean stack) {
        int number = Integer.parseInt(move.split(" ")[1]);
        int from = Integer.parseInt(move.split(" ")[3]) - 1; // index start at 0
        int to = Integer.parseInt(move.split(" ")[5]) - 1; // index start at 0

        String pileFrom = pile.get(from);
        String pileTo = pile.get(to);
        String toMove = pileFrom.substring(pileFrom.length() - number);
        if (!stack) {
            toMove = reverseString(toMove); // Move 1 by 1 = reverse the string
        }
        pileTo += toMove;
        pileFrom = pileFrom.substring(0, pileFrom.length() - number);
        pile.set(from, pileFrom);
        pile.set(to, pileTo);
    }

    private String reverseString(String str) {
        StringBuilder reverse = new StringBuilder();
        reverse.append(str);
        reverse.reverse();
        return reverse.toString();
    }
}
