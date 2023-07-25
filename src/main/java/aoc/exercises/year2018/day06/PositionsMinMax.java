package aoc.exercises.year2018.day06;

import aoc.common_objects.Position;

import java.util.List;

public record PositionsMinMax(List<Position> positions, int min, int max) {
}