package aoc.exercises.year2022.day23;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static aoc.exercises.year2022.day23.Grid.directions;

@Data
@AllArgsConstructor
public class Elve {
    private Position position;
    private Position target;
    private List<Direction> allowedMoves;

    public void moveIfPossible(List<Elve> elves) {
        if (checkTarget(elves)) {
            this.position = this.target;
        }
    }

    public void clearTarget() {
        this.target = null;
    }

    private boolean checkTarget(List<Elve> elves) {
        return elves.stream()
                .filter(elve -> !this.equals(elve))
                .noneMatch(elve -> this.getTarget().equals(elve.getTarget()));
    }

    public boolean canMove() {
        return !allowedMoves.isEmpty() && allowedMoves.size() != directions.size();
    }

    public boolean canMoveTo(Direction direction) {
        return allowedMoves.contains(direction);
    }
}