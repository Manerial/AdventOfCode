package aoc.exercises.year2019.day10;

import aoc.common_objects.Position;
import aoc.common_objects.RoundDeque;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Position> asteroids = inputParser.parseInput();
        Pair<Position, Integer> maxAsteroid = asteroids.stream()
                .map(asteroid -> new ImmutablePair<>(asteroid, this.countVisibleAsteroid(asteroid, asteroids)))
                .max(java.util.Map.Entry.comparingByValue())
                .orElseThrow();
        solution1 = maxAsteroid.getValue();

        List<Position> destroyedAsteroids = destroyAsteroids(maxAsteroid.getKey(), asteroids);
        // The 200th destroyed asteroid is at position 199.
        Position destroyAt200 = destroyedAsteroids.get(199);
        solution2 = destroyAt200.getX() * 100 + destroyAt200.getY();
    }

    /**
     * Count the asteroids visible from currentAsteroid.
     * An other asteroid is hidden if it is on the same vision line than an other one, but with a greater Manhattan distance
     *
     * @param currentAsteroid : the asteroid from which we want to look at other ones
     * @param asteroids       : the list of all asteroids
     * @return the number of asteroids visibles from currentAsteroid
     */
    private int countVisibleAsteroid(Position currentAsteroid, List<Position> asteroids) {
        List<Position> visionLines = new ArrayList<>();
        for (Position otherAsteroid : asteroids) {
            if (currentAsteroid == otherAsteroid) {
                continue;
            }
            Position visionLine = this.getVisionLine(currentAsteroid, otherAsteroid);
            if (!visionLines.contains(visionLine)) {
                visionLines.add(visionLine);
            }
        }
        return visionLines.size();
    }

    /**
     * Return the minimal vision line (vector from 0:0) from the asteroid to the otherAsteroid.
     * If two vectors are identical, then an asteroid is hidden behind an other one.
     *
     * @param asteroid      : the first asteroid
     * @param otherAsteroid : the second asteroid
     * @return the minimal vector from the asteroid to the otherAsteroid
     */
    private Position getVisionLine(Position asteroid, Position otherAsteroid) {
        int dx = asteroid.getX() - otherAsteroid.getX();
        int dy = asteroid.getY() - otherAsteroid.getY();
        int absX = Math.abs(dx);
        int absY = Math.abs(dy);
        if (dx == 0) {
            return new Position(0, dy / absY);
        } else if (dy == 0) {
            return new Position(dx / absX, 0);
        }
        int pgcd = 1;
        for (int i = 1; i <= absX && i <= absY; i++) {
            if (absX % i == 0 && absY % i == 0) {
                pgcd = i;
            }
        }
        return new Position(dx / pgcd, dy / pgcd);
    }

    /**
     * Destroy all asteroids on the list using a rotating ray centered on centerAsteroid.
     *
     * @param centerAsteroid : the asteroid from which we want to destroy others
     * @param asteroids      : the list of all asteroids
     * @return the list of all destroyed asteroids, sorted by their destruction index
     */
    private List<Position> destroyAsteroids(Position centerAsteroid, List<Position> asteroids) {
        // We don't want to touch the current one
        asteroids.remove(centerAsteroid);

        List<Position> destroyedAsteroids = new ArrayList<>();
        // For each asteroid, get the angle from the northest position (in radian)
        Map<Position, Double> anglesByPosition = asteroids.stream()
                .collect(Collectors.toMap(Function.identity(), asteroid ->
                        Math.atan2((asteroid.getX() - centerAsteroid.getX()), (asteroid.getY() - centerAsteroid.getY()))
                ));

        // Sort the asteroids by their radian, then their manhattan distance (to get the nearest ones first)
        RoundDeque<Map.Entry<Position, Double>> asteroidsSortedByRadian = new RoundDeque<>();
        asteroidsSortedByRadian.addAll(anglesByPosition.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> centerAsteroid.getManhattanDistance(o.getKey())))
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .toList());

        while (!asteroidsSortedByRadian.isEmpty()) {
            Map.Entry<Position, Double> currentAsteroid = asteroidsSortedByRadian.getFirst();
            destroyedAsteroids.add(currentAsteroid.getKey());
            asteroidsSortedByRadian.removeFirst();

            rotateRay(asteroidsSortedByRadian, currentAsteroid.getValue());
        }

        return destroyedAsteroids;
    }

    /**
     * Select the next asteroid to destroy.
     *
     * @param asteroidsSortedByRadian : the list of asteroids sorted by their radian
     * @param currentRadian           : the radian of the current asteroid
     */
    private static void rotateRay(RoundDeque<Map.Entry<Position, Double>> asteroidsSortedByRadian, double currentRadian) {
        if (asteroidsSortedByRadian.isEmpty()) {
            return;
        }
        Position checkForLoop = asteroidsSortedByRadian.getFirst().getKey();
        while (asteroidsSortedByRadian.getFirst().getValue() == currentRadian) {
            asteroidsSortedByRadian.rotate(1);

            // If we get the same object again, then we only have a line of undestroyed asteroids left
            if (checkForLoop.equals(asteroidsSortedByRadian.getFirst().getKey())) {
                break;
            }
        }
    }
}