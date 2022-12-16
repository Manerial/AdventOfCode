package adventofcode.aoc2022.day16;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class ValveRoom {
    private final String name;
    private final int flow;
    private final Map<String, Integer> connectedRoomsDistance;
    @Setter
    private boolean isOpen = false;

    public Integer getRoomDistance(String roomName) {
        return connectedRoomsDistance.get(roomName);
    }

    public int getFlowByTime(int time) {
        return flow * time;
    }

    public void addConnectedRoomDistance(String valveRoomName, int distance) {
        connectedRoomsDistance.put(valveRoomName, distance);
    }

    @Override
    public String toString() {
        return "Room " + name +
                " -> Valve open : " + isOpen +
                "; Flow : " + flow +
                "; Connections : " + connectedRoomsDistance.keySet().stream().map(s -> s + " " + connectedRoomsDistance.get(s)).sorted().collect(Collectors.toList());
    }
}
