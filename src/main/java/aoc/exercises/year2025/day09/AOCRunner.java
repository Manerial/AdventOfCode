package aoc.exercises.year2025.day09;

import aoc.common_objects.*;
import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/9">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    List<Position> redTilesList;
    List<Position> greenTilesList;
    Set<Position> redTilesSet;
    Set<Position> greenTilesSet;


    @Override
    public void run() {
        redTilesList = new ArrayList<>(inputList.parallelStream()
                .map(s -> new Position(s, ","))
                .toList());
        greenTilesList = getGreenTiles();

        redTilesSet = new HashSet<>(redTilesList);
        greenTilesSet = new HashSet<>(greenTilesList);

        long maxArea = 0;
        long maxGreenArea = 0;
        for (Position position : redTilesList) {
            long currentMax1 = redTilesList.parallelStream()
                    .mapToLong(p -> getArea(position, p))
                    .max()
                    .orElseThrow();
            long currentMax2 = getGreenMaxArea(position, maxGreenArea);

            maxArea = Math.max(maxArea, currentMax1);
            maxGreenArea = Math.max(maxGreenArea, currentMax2);
        }
        solution1 = maxArea;
        solution2 = maxGreenArea;
    }

    private List<Position> getGreenTiles() {
        List<Position> redTilesCopy = new ArrayList<>(redTilesList);
        redTilesCopy.add(redTilesCopy.getFirst());
        return new ArrayList<>(Position.shape(redTilesCopy));
    }

    private long getGreenMaxArea(Position position, long maxGreenArea) {
        return redTilesList.parallelStream()
                .filter(p -> !p.equals(position))
                .mapToLong(p -> getGreenArea(position, p, maxGreenArea))
                .max()
                .orElseThrow();
    }

    private long getArea(Position p1, Position p2) {
        int deltaX;
        int deltaY;
        if (p1.getX() > p2.getX()) {
            deltaX = p1.getX() - p2.getX() + 1;
        } else {
            deltaX = p2.getX() - p1.getX() + 1;
        }
        if (p1.getY() > p2.getY()) {
            deltaY = p1.getY() - p2.getY() + 1;
        } else {
            deltaY = p2.getY() - p1.getY() + 1;
        }
        return (long) deltaX * deltaY;
    }

    private long getGreenArea(Position p1, Position p2, long finalMaxArea) {
        long area = getArea(p1, p2);
        if (area < finalMaxArea) {
            return 0;
        }
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());

        // redTilesList is short, parallelStream will lose time
        boolean interrupt1 = redTilesList.stream()
                .anyMatch(p3 -> p3.getX() > minX && p3.getX() < maxX && p3.getY() > minY && p3.getY() < maxY);

        // greenTilesList is longer, parallelStream will gain a lot of time
        boolean interrupt2 = greenTilesList.parallelStream()
                .anyMatch(p3 -> p3.getX() > minX && p3.getX() < maxX && p3.getY() > minY && p3.getY() < maxY);
        if (interrupt1 || interrupt2) {
            return 0;
        }

        Position opposite1 = new Position(p1.getX(), p2.getY());
        Position opposite2 = new Position(p2.getX(), p1.getY());
        boolean opposite1InShape = isInShape(opposite1);
        boolean opposite2InShape = isInShape(opposite2);
        if (opposite1InShape && opposite2InShape) {
            return area;
        }
        return 0;
    }

    private boolean isInShape(Position position) {
        if (redTilesSet.contains(position) || greenTilesSet.contains(position)) {
            return true;
        }
        int y = position.getY();
        int countSectionsBeforePos = 0;
        List<Position> line = greenTilesList.parallelStream()
                .filter(p -> p.getY() == y)
                .sorted(Position::minFromOrigin)
                .toList();
        Position currentTile = new Position(0, y);

        while (currentTile.getX() <= position.getX()) {
            Position nextTile = getNextTile(line, currentTile.getX());
            if (nextTile == null) {
                break;
            }
            if (nextTile.getX() > position.getX()) {
                break;
            }
            if (redTilesSet.contains(nextTile)) {
                int index = redTilesList.indexOf(nextTile);
                Position prev = redTilesList.get(mod(index - 1, redTilesList.size()));
                Position prev2 = redTilesList.get(mod(index - 2, redTilesList.size()));
                Position next = redTilesList.get(mod(index + 1, redTilesList.size()));
                Position next2 = redTilesList.get(mod(index + 2, redTilesList.size()));

                boolean isLine;
                if (prev.getY() == y) {
                    nextTile = prev;
                    isLine = prev2.getY() < y && next.getY() > y || prev2.getY() > y && next.getY() < y;
                } else {
                    nextTile = next;
                    isLine = prev.getY() < y && next2.getY() > y || prev.getY() > y && next2.getY() < y;
                }
                if (isLine) {
                    countSectionsBeforePos++;
                }
            } else {
                countSectionsBeforePos++;
            }
            currentTile = nextTile.getEast();
        }

        return countSectionsBeforePos % 2 == 1;
    }

    private static Position getNextTile(List<Position> line, int x) {
        return line.parallelStream()
                .filter(linePos -> linePos.getX() > x)
                .findFirst()
                .orElse(null);
    }

    private static int mod(int num, int mod) {
        return (num % mod + mod) % mod;
    }
}