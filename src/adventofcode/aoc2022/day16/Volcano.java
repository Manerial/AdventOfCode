package adventofcode.aoc2022.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private Map<String, ValveRoom> valveRooms;

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
                    .filter(s -> roomTo.getConnectedRoomsDistance().get(s) == 1)
                    .collect(Collectors.toList());
            for (String nextRoomName : nextRooms) {
                recursiveSetDistance(roomFrom, valveRooms.get(nextRoomName), distance + 1);
            }
        }
    }

    public List<Map<String, Integer>> getAllFlowMapForCurrentRoom(String currentRoom, Map<String, Integer> previousValveOpenAtTime, int timeBeforeBoom) {
        List<Map<String, Integer>> path = new ArrayList<>();
        Map<String, Integer> flowMapCurrentRoom = getFlowMapForCurrentRoom(timeBeforeBoom, currentRoom);
        boolean isEnd = true;
        for (String nextRoom : flowMapCurrentRoom.keySet()) {
            // Si on a encore des salles non ouvertes, on va dans la prochaine
            if (!previousValveOpenAtTime.containsKey(nextRoom)) {
                int newTimeBeforeBoom = timeBeforeBoom - TIME_TO_OPEN - getCurrentRoomDistance(valveRooms.get(nextRoom), currentRoom);
                if(newTimeBeforeBoom > 0) {
                    Map<String, Integer> newValveOpenAtTime = new HashMap<>(previousValveOpenAtTime);
                    newValveOpenAtTime.put(nextRoom, flowMapCurrentRoom.get(nextRoom));
                    List<Map<String, Integer>> subList = getAllFlowMapForCurrentRoom(nextRoom, newValveOpenAtTime, newTimeBeforeBoom);
                    path.addAll(subList);
                    isEnd = false;
                }
            }
        }
        if(isEnd) {
            path.add(previousValveOpenAtTime);
        }

        return path;
    }

    private Map<String, Integer> getFlowMapForCurrentRoom(int timeBeforeBoom, String currentRoom) {
        Map<String, Integer> flowMapCurrentRoom = new HashMap<>();
        valveRooms.keySet().stream()
                .sorted()
                .filter(room -> valveRooms.get(room).getFlow() > 0)
                .forEach(room -> {
                    ValveRoom valveRoom = valveRooms.get(room);
                    int flowTime = valveRoom.getFlowByTime(timeBeforeBoom - getCurrentRoomDistance(valveRoom, currentRoom) - 1);
                    flowMapCurrentRoom.put(room, flowTime);
                });
        return flowMapCurrentRoom;
    }

    private int getCurrentRoomDistance(ValveRoom valveRoom, String currentRoom) {
        return valveRoom.getConnectedRoomsDistance().get(currentRoom);
    }
}
