package aoc.exercises.year2022.day15;

import utilities.AbstractAOC;
import aoc.common_objects.Position;
import aoc.common_objects.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/15">here</a>
 * </pre>
 */
// TODO : Need optimization
public class AOCRunner extends AbstractAOC {
    private final List<Sensor> sensors = new ArrayList<>();

    @Override
    public void run() {
        int yCoordinate = 2000000;
        if(isExample) {
            yCoordinate = 10;
        }
        fillBeacons(inputList);

        List<Range> distinctEmptyZones = getDistinctEmptyZones(yCoordinate);
        List<Position> beacons = getDistinctBeaconsAtCoordinates(yCoordinate);
        solution1 = distinctEmptyZones.stream().map(Range::size).reduce(Integer::sum).orElse(0) - beacons.size();

        Position nonScannedPosition = getNonScannedPosition(yCoordinate);
        solution2 = nonScannedPosition.getX() * 4000000L + nonScannedPosition.getY();
    }

    private void fillBeacons(List<String> list) {
        list.stream()
                .map(AOCRunner::cleanInput)
                .forEach(this::addSensor);
    }

    private static String[] cleanInput(String s) {
        return s.replace("Sensor at x=", "")
                .replace(" closest beacon is at x=", "")
                .replace(" y=", "")
                .split(":");
    }

    private void addSensor(String[] strings) {
        Position sensorPosition = new Position(strings[0], ",");
        Position beaconPosition = new Position(strings[1], ",");
        Sensor sensor = new Sensor(sensorPosition, beaconPosition);
        sensors.add(sensor);
    }

    private List<Range> getDistinctEmptyZones(int yCoordinate) {
        List<Range> emptyLines = sensors.stream()
                .map(sensor -> sensor.scanEmptyLine(yCoordinate))
                .filter(Objects::nonNull)
                .map(line -> new Range(line.left.getX(), line.right.getX()))
                .toList();

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
            for (Range savedRange : mergedRanges) {
                if (savedRange.canBeMerge(range)) {
                    savedRange.merge(range);
                    merged = true;
                }
            }
            if (!merged) {
                mergedRanges.add(range);
            }
        }
        return mergedRanges;
    }

    private List<Position> getDistinctBeaconsAtCoordinates(int yCoordinate) {
        return sensors.stream()
                .map(Sensor::getNearestBeaconPosition)
                .filter(position -> position.getY() == yCoordinate)
                .distinct()
                .toList();
    }

    private Position getNonScannedPosition(int yCoordinate) {
        for (int y = 0; y <= yCoordinate * 2; y++) {
            List<Range> distinctEmptyZones = getDistinctEmptyZones(y);
            if (distinctEmptyZones.size() > 1) {
                Collections.sort(distinctEmptyZones);
                int x = distinctEmptyZones.get(1).getBorneMin() - 1;
                return new Position(x, y);
            }
        }
        return new Position(0, 0);
    }
}
