package aoc.exercises.year2024.day08;

import aoc.common_objects.Position;
import lombok.Data;

import java.util.*;

@Data
public class AntennaMap {
    private final Map<Position, Character> map = new HashMap<>();
    private final Set<Character> antennaTypes = new HashSet<>();
    private Set<Position> antinodePositions;
    private boolean allLine;

    public void addLineAtY(String s, int y) {
        for (int x = 0; x < s.length(); x++) {
            char charAtX = s.charAt(x);
            map.put(new Position(x, y), charAtX);
            if (charAtX != '.') {
                antennaTypes.add(charAtX);
            }
        }
    }

    public long countAntinodesWithinMap() {
        antinodePositions = new HashSet<>();
        for (Character antennaType : antennaTypes) {
            List<Position> antennasOfType = map.entrySet().stream()
                    .filter(entry -> Objects.equals(entry.getValue(), antennaType))
                    .map(Map.Entry::getKey)
                    .toList();

            antennasOfType.forEach(currentAntenna -> saveAntinodesForOtherAntennas(currentAntenna, antennasOfType));
        }
        return antinodePositions.size();
    }

    private void saveAntinodesForOtherAntennas(Position currentAntenna, List<Position> antennasOfType) {
        antennasOfType.stream()
                .filter(otherAntenna -> !currentAntenna.equals(otherAntenna))
                .forEach(otherAntenna -> saveAntinodesForTwoAntennas(currentAntenna, otherAntenna));
    }

    private void saveAntinodesForTwoAntennas(Position currentAntenna, Position otherAntenna) {
        int deltaX = otherAntenna.getX() - currentAntenna.getX();
        int deltaY = otherAntenna.getY() - currentAntenna.getY();

        if (!allLine) {
            int antinodeX = currentAntenna.getX() + (deltaX) * 2;
            int antinodeY = currentAntenna.getY() + (deltaY) * 2;
            Position antinode = new Position(antinodeX, antinodeY);
            if (map.containsKey(antinode)) {
                antinodePositions.add(antinode);
            }
            return;
        }

        int step = 1;
        int antinodeX = currentAntenna.getX() + (deltaX) * step;
        int antinodeY = currentAntenna.getY() + (deltaY) * step;
        Position antinode = new Position(antinodeX, antinodeY);

        while (map.containsKey(antinode)) {
            antinodePositions.add(antinode);
            step++;
            antinodeX = currentAntenna.getX() + (deltaX) * step;
            antinodeY = currentAntenna.getY() + (deltaY) * step;
            antinode = new Position(antinodeX, antinodeY);
        }
    }
}
