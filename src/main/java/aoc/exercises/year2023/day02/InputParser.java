package aoc.exercises.year2023.day02;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<List<Game>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Game> parseInput() {
        List<Game> games = new ArrayList<>();
        inputList.forEach(s -> {
            String[] parts = s.split(": ");
            int id = Integer.parseInt(parts[0].replace("Game ", ""));
            Game game = new Game(id);

            String[] setsStr = parts[1].split("; ");
            for (String setStr : setsStr) {
                String[] piecesStr = setStr.split(", ");
                Map<String, Integer> set = new HashMap<>();

                for (String pieceStr : piecesStr) {
                    int number = Integer.parseInt(pieceStr.split(" ")[0]);
                    String type = pieceStr.split(" ")[1];
                    set.put(type, number);
                }
                game.addSet(set);
            }
            games.add(game);
        });
        return games;
    }
}
