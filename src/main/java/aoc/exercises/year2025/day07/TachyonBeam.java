package aoc.exercises.year2025.day07;

import aoc.common_objects.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class TachyonBeam {
    private final Position position;
    private long distinctPasts;

    public TachyonBeam getNext() {
        return new TachyonBeam(position.getSouth(), distinctPasts);
    }

    public void addDistinctPasts(long distinctPasts) {
        this.distinctPasts += distinctPasts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TachyonBeam that = (TachyonBeam) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
