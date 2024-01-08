package aoc.exercises.year2023.day02;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Game {
    private int id;
    private List<Map<String, Integer>> setsOfPieces = new ArrayList<>();

    public Game(int id) {
        this.id = id;
    }

    public void addSet(Map<String, Integer> set) {
        setsOfPieces.add(set);
    }

    public int getIdIfPossible(Map<String, Integer> set) {
        if (setsOfPieces.stream().allMatch(setOfPiece -> checkSet(setOfPiece, set))) {
            return id;
        }
        return 0;
    }

    private boolean checkSet(Map<String, Integer> setOfPiece, Map<String, Integer> set) {
        for (Map.Entry<String, Integer> entry : setOfPiece.entrySet()) {
            if (!set.containsKey(entry.getKey()) || set.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public int getMaxOfColor(String color) {
        return setsOfPieces.stream()
                .mapToInt(set -> set.getOrDefault(color, 0))
                .max()
                .orElse(0);
    }
}
