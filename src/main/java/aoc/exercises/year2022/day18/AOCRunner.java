package aoc.exercises.year2022.day18;

import aoc.common_objects.Position3D;
import utilities.AbstractAOC;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/18">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Position3D startPosition;

    @Override
    public void run() {
        Set<Position3D> lavaDrop = inputList.stream().map(line -> new Position3D(line, ",")).collect(Collectors.toSet());
        Set<Position3D> cube = getCube(lavaDrop);
        Set<Position3D> outerBubble = new HashSet<>();
        getBubble(cube, lavaDrop, outerBubble, Set.of(startPosition));
        Set<Position3D> innerBubble = cube.stream()
                .filter(position3D -> !lavaDrop.contains(position3D) && !outerBubble.contains(position3D))
                .collect(Collectors.toSet());

        long surface = lavaDrop.stream()
                .mapToLong(position3D -> 6 - position3D.countNeighbors(lavaDrop))
                .sum();
        long bubbleSurface = innerBubble.stream()
                .mapToLong(position3D -> 6 - position3D.countNeighbors(innerBubble))
                .sum();

        solution1 = surface;
        solution2 = surface - bubbleSurface;
    }

    private Set<Position3D> getCube(Set<Position3D> lavaDrop) {
        Set<Position3D> cube = new HashSet<>();
        int min = lavaDrop.stream()
                .map(position3D -> List.of(position3D.getX(), position3D.getY(), position3D.getZ()))
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .min()
                .orElseThrow() - 1;
        int max = lavaDrop.stream()
                .map(position3D -> List.of(position3D.getX(), position3D.getY(), position3D.getZ()))
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow() + 1;
        startPosition = new Position3D(min, min, min);

        for (int x = min; x <= max; x++) {
            for (int y = min; y <= max; y++) {
                for (int z = min; z <= max; z++) {
                    Position3D position = new Position3D(x, y, z);
                    cube.add(position);
                }
            }
        }

        return cube;
    }

    private void getBubble(Set<Position3D> cube, Set<Position3D> lavaDrop, Set<Position3D> bubble, Set<Position3D> positions) {
        bubble.addAll(positions);
        Set<Position3D> neighbors = positions.stream()
                .map(Position3D::getNeighbors)
                .flatMap(Set::stream)
                .filter(position3D -> cube.contains(position3D) && !lavaDrop.contains(position3D) && !bubble.contains(position3D))
                .collect(Collectors.toSet());
        if (!neighbors.isEmpty()) {
            getBubble(cube, lavaDrop, bubble, neighbors);
        }
    }
}