package aoc.exercises.year2022.day16;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ValveRoom {
    @Setter
    private Integer index;
    private int flow;
    private Map<Integer, Integer> roomDistanceByIndex;

    public void updateDistance(Integer roomIndex, int distance) {
        roomDistanceByIndex.put(roomIndex, distance);
    }

    public int getRoomDistanceByIndex(Integer roomIndex) {
        return roomDistanceByIndex.get(roomIndex);
    }

    public Integer getRoomDistanceByIndexOrDefault(Integer roomIndex, int defaultValue) {
        return roomDistanceByIndex.getOrDefault(roomIndex, defaultValue);
    }

    public void keepOnly(Integer[] usedRoom) {
        Set<Integer> unusedRooms = new HashSet<>(roomDistanceByIndex.keySet());
        List.of(usedRoom).forEach(unusedRooms::remove);
        for (Integer unusedRoomIndex : unusedRooms) {
            roomDistanceByIndex.remove(unusedRoomIndex);
        }
    }

    public int open(int time) {
        return flow * time;
    }
}
