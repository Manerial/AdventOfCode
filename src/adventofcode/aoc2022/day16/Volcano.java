package adventofcode.aoc2022.day16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private final Map<String, ValveRoom> valveRooms;

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

    public int search(List<String> searchOrder, String startRoomName, int startTime) {
        String currentRoomName = startRoomName;
        int timeBeforeBoom = startTime;
        int finalFlow = 0;
        for(String roomName : searchOrder) {
            ValveRoom currentRoom = valveRooms.get(currentRoomName);
            ValveRoom nextRoom = valveRooms.get(roomName);
            int timeToMove = currentRoom.getRoomDistance(roomName);
            timeBeforeBoom = newTimeAfterMoveAndOpen(timeBeforeBoom, timeToMove);
            if(timeBeforeBoom < 0) {
                break;
            }
            finalFlow += nextRoom.getFlowByTime(timeBeforeBoom);
            currentRoomName = roomName;
        }
        return finalFlow;
    }

    private int newTimeAfterMoveAndOpen(int timeBeforeBoom, int timeToMove) {
        return timeBeforeBoom - TIME_TO_OPEN - timeToMove;
    }

    public Map<String, Integer> getFlowMapForCurrentRoom(int timeBeforeBoom, String currentRoom) {
        Map<String, Integer> flowMapCurrentRoom = new HashMap<>();
        valveRooms.keySet().stream()
                .sorted()
                .filter(room -> valveRooms.get(room).getFlow() > 0)
                .forEach(room -> {
                    ValveRoom valveRoom = valveRooms.get(room);
                    int flowTime = valveRoom.getFlowByTime(newTimeAfterMoveAndOpen(timeBeforeBoom, valveRoom.getRoomDistance(currentRoom)));
                    flowMapCurrentRoom.put(room, flowTime);
                });
        return flowMapCurrentRoom;
    }
}
