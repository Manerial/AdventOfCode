package aoc.exercises.year2022.day22;

import aoc.common_objects.Position;
import aoc.common_objects.Position3D;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Face {
    private Map<Position3D, Position> positions = new HashMap<>();

    public boolean isAlreadyFilled() {
        return !positions.isEmpty();
    }
}
