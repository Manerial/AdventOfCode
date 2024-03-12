package aoc.exercises.year2018.day10;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2018/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Star> stars = inputParser.parseInput();

        int seconds = 0;
        while (!aligned(stars)) {
            seconds++;
            stars.forEach(Star::move);
        }

        solution1 = displayStars(stars);
        solution2 = seconds;
    }

    private String displayStars(List<Star> stars) {
        int minX = stars.stream().map(Star::position).mapToInt(Position::getX).min().orElseThrow();
        int maxX = stars.stream().map(Star::position).mapToInt(Position::getX).max().orElseThrow();
        int minY = stars.stream().map(Star::position).mapToInt(Position::getY).min().orElseThrow();
        int maxY = stars.stream().map(Star::position).mapToInt(Position::getY).max().orElseThrow();
        StringBuilder builder = new StringBuilder();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                if (stars.stream().anyMatch(star -> star.position().getX() == finalX && star.position().getY() == finalY)) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }
            builder.append("\r\n");
        }
        return builder.toString();
    }

    private boolean aligned(List<Star> stars) {
        for (Star star : stars) {
            if (!hasNeighbor(stars, star)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasNeighbor(List<Star> stars, Star star) {
        return stars.stream().anyMatch(star2 -> {
            if (star.equals(star2)) {
                return false;
            }
            return star.position().getManhattanX(star2.position()) <= 1 && star.position().getManhattanY(star2.position()) <= 1;
        });
    }
}