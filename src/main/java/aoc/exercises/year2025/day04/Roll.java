package aoc.exercises.year2025.day04;

import aoc.common_objects.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class Roll {
    private Position position;
    private List<Roll> neighbors = new ArrayList<>();
    private boolean isAccessible = true;

    public Roll(Position position) {
        this.position = position;
    }

    public void removeAllOccurrences(Map<Position, Roll> rolls) {
        rolls.remove(position);
        neighbors.forEach(roll -> roll.removeNeighbor(this));
    }

    public void addNeighbor(Roll roll) {
        neighbors.add(roll);
        isAccessible = neighbors.size() < 4;
    }

    public void removeNeighbor(Roll accessibleRolls) {
        neighbors.remove(accessibleRolls);
        isAccessible = neighbors.size() < 4;
    }
}
