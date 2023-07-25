package aoc.exercises.year2022.day17;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoopInfo {
    private RockShape rock;
    private int directionIndex;
    private int shapeXPosition;

    private int blocks;
    private int rockIndex;

    public boolean matches(LoopInfo loopInfo) {
        return rock == loopInfo.rock
                && directionIndex == loopInfo.directionIndex
                && shapeXPosition == loopInfo.shapeXPosition;
    }
}
