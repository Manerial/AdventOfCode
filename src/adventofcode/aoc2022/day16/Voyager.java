package adventofcode.aoc2022.day16;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Voyager {
    private final int id;
    private List<ValveRoom> visitedRooms;
    private int timeLeft;

    public Voyager(int id, ValveRoom startRoom) {
        this.id = id;
        visitedRooms = new ArrayList<>();
        this.addRoom(startRoom);
    }

    public Voyager(Voyager voyager) {
        this(voyager.id, null);
        this.visitedRooms = ValveRoom.copyList(voyager.visitedRooms);
        this.timeLeft = this.visitedRooms.stream().map(ValveRoom::getTimeOpen).reduce(Integer::min).orElse(0);
    }

    public static List<Voyager> copyList(List<Voyager> voyagers) {
        List<Voyager> copyOfVoyagers = new ArrayList<>();
        for (Voyager voyager : voyagers) {
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
        if(room == null) {
            return;
        }
        this.visitedRooms.add(room);
        timeLeft = room.getTimeOpen();
    }

    public ValveRoom getLastRoom() {
        return visitedRooms.get(visitedRooms.size() - 1);
    }

    @Override
    public String toString() {
        String valveRoomsStr = this.visitedRooms.stream().map(ValveRoom::getName).reduce((s, s2) -> s + " " + s2).orElse("");
        return "Voyager : " + this.id + " TimeLeft : " + this.timeLeft + valveRoomsStr + "\r\n";
    }
}
