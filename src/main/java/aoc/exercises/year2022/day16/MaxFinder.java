package aoc.exercises.year2022.day16;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

@Getter
public class MaxFinder {
    private int maxPressure = 0;

    public int findMaxPressure(ValveRoom currentRoom, ValveRoom[] roomsToVisit, Explorer[] explorers, Explorer currentExplorer, int previousPressure) {
        int roomPressure = currentRoom.open(currentExplorer.getTimeLeft());
        roomsToVisit[currentRoom.getIndex()] = null;

        if (currentRoom.getIndex() != 0) { // avoid both in same room
            int maxBorn = previousPressure + roomPressure + getMaxBorn(roomsToVisit, explorers);
            if (maxPressure > maxBorn) {
                return 0;
            }
        }
        int finalRoomPressure = Arrays.stream(roomsToVisit)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(ValveRoom::getFlow))
                .mapToInt(nextRoom -> getNextRoomMax(roomsToVisit, explorers, nextRoom, previousPressure + roomPressure))
                .max()
                .orElse(0);
        finalRoomPressure += roomPressure;
        maxPressure = Math.max(maxPressure, finalRoomPressure);
        return finalRoomPressure;
    }

    private int getMaxBorn(ValveRoom[] roomsToVisit, Explorer[] explorers) {
        // WARNING : TimeLifter might cause problems. Remove to get 100% right solution (longer)
        int[] timeLifter = new int[explorers.length];
        return Arrays.stream(roomsToVisit)
                .filter(Objects::nonNull)
                .mapToInt(room -> {
                    try {
                        Explorer explorer = prepareNearestExplorer(explorers, room);
                        int flow = room.open(explorer.getTimeLeft() - timeLifter[explorer.getIndex()]);
                        timeLifter[explorer.getIndex()] += explorers.length;
                        return flow;
                    } catch (NoSuchElementException ignored) {
                        return 0;
                    }
                })
                .sum();
    }

    private int getNextRoomMax(ValveRoom[] roomsToVisit, Explorer[] explorers, ValveRoom nextRoom, int currentPressure) {
        ValveRoom[] cRoomsToVisit = roomsToVisit.clone();
        Explorer[] cExplorers = explorers.clone();
        ValveRoom cNextRoom = cRoomsToVisit[nextRoom.getIndex()];
        try {
            Explorer nearestExplorer = prepareNearestExplorer(cExplorers, cNextRoom);
            cExplorers[nearestExplorer.getIndex()] = nearestExplorer;
            return findMaxPressure(cNextRoom, cRoomsToVisit, cExplorers, nearestExplorer, currentPressure);
        } catch (NoSuchElementException ignored) {
            return 0;
        }
    }

    private Explorer prepareNearestExplorer(Explorer[] explorers, ValveRoom nextRoom) {
        Explorer nearestExplorer = Arrays.stream(explorers)
                .filter(explorer -> explorer.hasTimeToExplore(nextRoom))
                .min(Comparator.comparingInt(explorer -> explorer.getDistanceTo(nextRoom)))
                .orElseThrow(NoSuchElementException::new);
        Explorer cloneExplorer = new Explorer(nearestExplorer);
        cloneExplorer.spendTimeFor(nextRoom);
        return cloneExplorer;
    }
}
