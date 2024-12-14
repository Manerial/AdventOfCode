package aoc.exercises.year2024.day14;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/14">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int maxX;
        int maxY;
        if (isExample) {
            maxX = 11;
            maxY = 7;
        } else {
            maxX = 101;
            maxY = 103;
        }
        List<Guard> guards = inputList.stream()
                .map(Guard::new)
                .toList();

        // assume solution2 is not within the first 100 steps
        guards.forEach(guard -> guard.move(100, maxX, maxY));

        long topLeft = guards.stream()
                .filter(guard -> guard.getPosition().getX() < maxX / 2 && guard.getPosition().getY() < maxY / 2)
                .count();

        long topRight = guards.stream()
                .filter(guard -> guard.getPosition().getX() > maxX / 2 && guard.getPosition().getY() < maxY / 2)
                .count();

        long bottomLeft = guards.stream()
                .filter(guard -> guard.getPosition().getX() < maxX / 2 && guard.getPosition().getY() > maxY / 2)
                .count();

        long bottomRight = guards.stream()
                .filter(guard -> guard.getPosition().getX() > maxX / 2 && guard.getPosition().getY() > maxY / 2)
                .count();

        solution1 = topLeft * topRight * bottomRight * bottomLeft;

        if (!isExample) {
            int seconds = 100;
            while (!found(guards, maxX, maxY)) {
                seconds++;
                guards.forEach(guard -> guard.move(1, maxX, maxY));
            }

            solution2 = seconds;
        } else {
            solution2 = "NOT APPLICABLE";
        }
    }

    private boolean found(List<Guard> guards, int maxX, int maxY) {
        Set<Position> guardPositions = guards.stream()
                .map(Guard::getPosition)
                .collect(Collectors.toSet());
        for (int y = 0; y < maxY; y++) {
            int line = 0;
            for (int x = 0; x < maxX; x++) {
                Position position = new Position(x, y);
                if (guardPositions.contains(position)) {
                    line++;
                    if (line == 10) {
                        return true;
                    }
                } else {
                    line = 0;
                }
            }
        }
        return false;
    }
}