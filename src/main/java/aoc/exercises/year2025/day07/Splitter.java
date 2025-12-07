package aoc.exercises.year2025.day07;

import aoc.common_objects.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Splitter {
    private final Position position;
    private boolean used;

    /**
     * Generate the two tachyon beams and update the list with them.
     *
     * @param tachyonBeams The list of tachyonBeams to update
     * @param tachyonBeam  The tachyonBeam to split
     */
    public void updateList(List<TachyonBeam> tachyonBeams, TachyonBeam tachyonBeam) {
        this.used = true;
        TachyonBeam left = new TachyonBeam(position.getEast(), tachyonBeam.getDistinctPasts());
        TachyonBeam right = new TachyonBeam(position.getWest(), tachyonBeam.getDistinctPasts());

        addToTachyonBeams(tachyonBeams, left);
        addToTachyonBeams(tachyonBeams, right);
    }

    /**
     * Check if the list already contains the tachyon beam then:
     * - If not present in the list, add it
     * - If already present, then increment it's distinctPasts
     *
     * @param tachyonBeams The list to update
     * @param tachyonBeam  the tachyon beam to add / use for update
     */
    private void addToTachyonBeams(List<TachyonBeam> tachyonBeams, TachyonBeam tachyonBeam) {
        if (tachyonBeams.contains(tachyonBeam)) {
            tachyonBeams.get(tachyonBeams.indexOf(tachyonBeam)).addDistinctPasts(tachyonBeam.getDistinctPasts());
        } else {
            tachyonBeams.add(tachyonBeam);
        }
    }
}
