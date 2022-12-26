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
    @Setter
    private int timeOpen = 1;

    public ValveRoom(ValveRoom valveRoom, int timeOpen) {
        this.name= valveRoom.name;
        this.flow = valveRoom.flow;
        this.connectedRoomsDistance = valveRoom.getConnectedRoomsDistance();
        this.isOpen = true;
        this.timeOpen = timeOpen;
    }

    public Integer getRoomDistance(String roomName) {
        return connectedRoomsDistance.get(roomName);
    }

    public int getFlow() {
        return flow * timeOpen;
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
