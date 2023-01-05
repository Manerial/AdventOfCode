package adventofcode.aoc2022.day16;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValveRoom {
    private final String name;
    private final int flow;
    private final Map<String, Integer> connectedRoomsDistance;
    @Setter
    private boolean isOpen = false;
    @Setter
    private int openTime = 1;

    public ValveRoom(ValveRoom valveRoom, int openTime) {
        this.name= valveRoom.name;
        this.flow = valveRoom.flow;
        this.connectedRoomsDistance = valveRoom.getConnectedRoomsDistance();
        this.isOpen = true;
        this.openTime = openTime;
    }

    public ValveRoom(ValveRoom valveRoom) {
        this.name= valveRoom.name;
        this.flow = valveRoom.flow;
        this.connectedRoomsDistance = valveRoom.getConnectedRoomsDistance();
        this.isOpen = true;
        this.openTime = valveRoom.openTime;
    }

    public static List<ValveRoom> copyList(List<ValveRoom> valveRooms) {
        List<ValveRoom> copyOfValveRooms = new ArrayList<>();
        for(ValveRoom valveRoom : valveRooms) {
            copyOfValveRooms.add(new ValveRoom(valveRoom));
        }
        return copyOfValveRooms;
    }

    public static boolean containsRoomName(List<String> rooms, String roomName) {
        return rooms.stream().anyMatch(s -> s.equals(roomName));
    }

    public Integer getRoomDistance(String roomName) {
        return connectedRoomsDistance.get(roomName);
    }

    public int getFlow() {
        return flow * openTime;
    }
    public int getInitialFlow() {
        return flow;
    }

    public void addConnectedRoomDistance(String valveRoomName, int distance) {
        connectedRoomsDistance.put(valveRoomName, distance);
    }

    @Override
    public String toString() {
        return "Room " + name +
                " -> Valve open : " + openTime +
                "; Flow : " + flow;
    }
}
