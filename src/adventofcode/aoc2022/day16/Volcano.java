package adventofcode.aoc2022.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private final Map<String, ValveRoom> valveRooms;

    public ValveRoom getValveRoom(String aa) {
        return valveRooms.get(aa);
    }

    public Volcano() {
        valveRooms = new HashMap<>();
    }

    public void addValveRoom(String name, ValveRoom valveRoom) {
        this.valveRooms.put(name, valveRoom);
    }

    public void computeDistances() {
        for (ValveRoom valveRoom : this.valveRooms.values()) {
            recursiveSetDistance(valveRoom.getName(), valveRoom, 0);
        }
    }

    private void recursiveSetDistance(String roomFrom, ValveRoom roomTo, int distance) {
        Integer savedDistance = roomTo.getConnectedRoomsDistance().get(roomFrom);
        if (savedDistance == null || savedDistance > distance || distance == 1) {
            if (distance != 1) {
                roomTo.addConnectedRoomDistance(roomFrom, distance);
            }
            List<String> nextRooms = roomTo.getConnectedRoomsDistance().keySet().stream()
                    .filter(s -> roomTo.getRoomDistance(s) == 1)
                    .collect(Collectors.toList());
            for (String nextRoomName : nextRooms) {
                recursiveSetDistance(roomFrom, valveRooms.get(nextRoomName), distance + 1);
            }
        }
    }

    public Integer getMaxPresure1(ValveRoom startRoom, int timeBeforeBoom) {
        List<Integer> maxPreasures = getMaxPresureList(startRoom, timeBeforeBoom, new ArrayList<>());
        return maxPreasures.stream().reduce(Integer::max).orElse(0);
    }

    public Integer getMaxPresure2(ValveRoom startRoom, int timeBeforeBoom) {
        return 0;
    }

    public List<Integer> getMaxPresureList(ValveRoom currentRoom, int timeBeforeBoom, List<ValveRoom> alreadyVisitedRooms) {
        List<Integer> maxPreasures = new ArrayList<>();
        boolean isComplete = true;
        for (String nextRoomName : getFlowMapExcluding(alreadyVisitedRooms)) {
            int newTimeBeforeBoom = newTimeAfterMoveAndOpen(timeBeforeBoom, currentRoom, nextRoomName);
            if (newTimeBeforeBoom > 0) {
                List<ValveRoom> newAlreadyVisitedRooms = new ArrayList<>(alreadyVisitedRooms);
                ValveRoom nextRoom = goIntoNextRoom(nextRoomName, newTimeBeforeBoom);
                newAlreadyVisitedRooms.add(nextRoom);

                maxPreasures.addAll(getMaxPresureList(nextRoom, newTimeBeforeBoom, newAlreadyVisitedRooms));
                isComplete = false;
            }
        }
        if (isComplete) {
            int maxPreasure = alreadyVisitedRooms.stream().map(ValveRoom::getFlow).reduce(Integer::sum).orElse(0);
            maxPreasures.add(maxPreasure);
        }

        return maxPreasures;
    }

    private ValveRoom goIntoNextRoom(String nextRoomName, int newTimeBeforeBoom) {
        return new ValveRoom(getValveRoom(nextRoomName), newTimeBeforeBoom);
    }

    private int newTimeAfterMoveAndOpen(int timeBeforeBoom, ValveRoom currentRoom, String nextRoomName) {
        return timeBeforeBoom - getTimeToMoveAndOpen(currentRoom, nextRoomName);
    }

    private int getTimeToMoveAndOpen(ValveRoom currentRoom, String nextRoomName) {
        return currentRoom.getRoomDistance(nextRoomName) + TIME_TO_OPEN;
    }

    private List<String> getFlowMapExcluding(List<ValveRoom> alreadyVisitedRooms) {
        List<String> alreadyVisitedRoomsNames = alreadyVisitedRooms.stream().map(ValveRoom::getName).collect(Collectors.toList());
        return valveRooms.keySet().stream()
                .sorted()
                .filter(roomName -> valveRooms.get(roomName).getFlow() > 0)
                .filter(roomName -> !alreadyVisitedRoomsNames.contains(roomName))
                .collect(Collectors.toList());
    }
}
