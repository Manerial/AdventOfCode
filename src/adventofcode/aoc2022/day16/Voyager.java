package adventofcode.aoc2022.day16;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Voyager {
    private List<ValveRoom> visitedRooms;
    private int timeLeft;

    public Voyager(int timeLeft, ValveRoom startRoom) {
        visitedRooms = new ArrayList<>();
        visitedRooms.add(startRoom);
        this.timeLeft = timeLeft;
    }

    public Voyager(Voyager voyager) {
        this(voyager.timeLeft, null);
        this.visitedRooms = ValveRoom.copyList(voyager.visitedRooms);
    }

    public static List<Voyager> copyList(List<Voyager> voyagers) {
        List<Voyager> copyOfVoyagers = new ArrayList<>();
        for(Voyager voyager : voyagers) {
            copyOfVoyagers.add(new Voyager(voyager));
        }
        return copyOfVoyagers;
    }

    public static int getPreasure(List<Voyager> voyagers) {
        return voyagers.stream()
            .mapToInt(voyager -> voyager.visitedRooms.stream()
                    .mapToInt(ValveRoom::getFlow)
                    .sum())
            .sum();
    }

    public void addRoom(ValveRoom room) {
        this.visitedRooms.add(room);
        timeLeft = room.getTimeOpen();
    }

    public ValveRoom getLastRoom() {
        return visitedRooms.get(visitedRooms.size() - 1);
    }
}
