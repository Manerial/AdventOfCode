package aoc.exercises.year2022.day16;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Explorer {
    private int index;
    private int timeLeft;
    private int currentRoomIndex;

    public Explorer(Explorer that) {
        this.index = that.index;
        this.currentRoomIndex = that.currentRoomIndex;
        this.timeLeft = that.timeLeft;
    }

    public boolean hasTimeToExplore(ValveRoom valveRoom) {
        return timeLeft > valveRoom.getRoomDistanceByIndex(currentRoomIndex);
    }

    public void spendTimeFor(ValveRoom nextRoom) {
        int distance = nextRoom.getRoomDistanceByIndex(currentRoomIndex);
        currentRoomIndex = nextRoom.getIndex();
        timeLeft = timeLeft - 1 - distance;
    }

    public int getDistanceTo(ValveRoom nextRoom) {
        return nextRoom.getRoomDistanceByIndex(currentRoomIndex);
    }
}
