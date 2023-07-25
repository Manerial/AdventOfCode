package aoc.exercises.year2021.day04;

import lombok.AllArgsConstructor;
import lombok.Data;
import aoc.common_objects.Position;

@Data
@AllArgsConstructor
public class BingoCell {
    private Position position;
    private int value;
    private boolean drawn;
}
