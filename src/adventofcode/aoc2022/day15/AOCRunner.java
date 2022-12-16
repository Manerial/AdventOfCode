package adventofcode.aoc2022.day15;

import org.apache.commons.lang3.tuple.ImmutablePair;
import template.AOC;
import utilities.FileLoader;
import utilities.Position;
import utilities.Printer;
import utilities.Range;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * AdventOfCode 2022 day 14's instructions are <a href="https://adventofcode.com/2022/day/14">here</a>
 */
public class AOCRunner implements AOC {
    private final List<Sensor> sensors = new ArrayList<>();

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            int yCoordinate = 2000000;
            fillBeacons(list);

            List<Range> distinctEmptyZones = getDistinctEmptyZones(yCoordinate);
            List<Position> beacons = getDistinctBeaconsAtCoordinates(yCoordinate);
            int emptyZones = distinctEmptyZones.stream().map(Range::size).reduce(Integer::sum).orElse(0) - beacons.size();
            Printer.println(emptyZones);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillBeacons(List<String> list) {
        list.stream()
                .map(s -> s
                        .replace("Sensor at x=", "")
                        .replace(" closest beacon is at x=", "")
                        .replace(" y=", "")
                        .split(":")
                ).forEach(this::addSensor);
    }

    private void addSensor(String[] strings) {
        Position sensorPosition = new Position(strings[0], ",");
        Position beaconPosition = new Position(strings[1], ",");
        Sensor sensor = new Sensor(sensorPosition, beaconPosition);
        sensors.add(sensor);
    }

    private List<Range> getDistinctEmptyZones(int yCoordinate) {
        List<Range> emptyLines = new ArrayList<>();
        for (Sensor sensor : sensors) {
            ImmutablePair<Position, Position> emptyLine = sensor.scanEmptyLine(yCoordinate);
            Range range = new Range(emptyLine.left.getX(), emptyLine.right.getX());
            emptyLines.add(range);
        }

        return getMergedRanges(emptyLines);
    }

    private List<Range> getMergedRanges(List<Range> emptyLines) {
        List<Range> oldMergedRanges;
        List<Range> newMergedRanges = mergeListOfRanges(emptyLines);
        do {
            oldMergedRanges = newMergedRanges;
            newMergedRanges = mergeListOfRanges(oldMergedRanges);
        } while (!newMergedRanges.equals(oldMergedRanges));
        return oldMergedRanges;
    }

    private List<Range> mergeListOfRanges(List<Range> emptyLines) {
        List<Range> mergedRanges = new ArrayList<>();
        mergedRanges.add(emptyLines.get(0));
        for (Range range : emptyLines) {
            boolean merged = false;
            for(Range savedRange : mergedRanges) {
                if (savedRange.canBeMerge(range)) {
                    savedRange.merge(range);
                    merged = true;
                }
            }
            if(!merged) {
                mergedRanges.add(range);
            }
        }
        return mergedRanges;
    }

    private List<Position> getDistinctBeaconsAtCoordinates(int yCoordinate) {
        List<Position> beacons = new ArrayList<>();

        for(Sensor sensor : sensors) {
            if (!beacons.contains(sensor.getNearestBeaconPosition()) && sensor.getNearestBeaconPosition().getY() == yCoordinate) {
                beacons.add(sensor.getNearestBeaconPosition());
            }
        }
        return beacons;
    }
}
