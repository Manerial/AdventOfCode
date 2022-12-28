package adventofcode.aoc2022.day16;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private final Map<String, ValveRoom> valveRooms;
    private int maxPreasure;
    private int timeBeforeBoom;

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

    public Integer getMaxPresure(ValveRoom startRoom, int timeBeforeBoom, int groups) {
        List<Voyager> voyagers = new ArrayList<>();
        for (int group = 0; group < groups; group++) {
            Voyager voyager = new Voyager(timeBeforeBoom, startRoom);
            voyagers.add(voyager);
        }
        maxPreasure = 0;
        this.timeBeforeBoom = timeBeforeBoom;
        computeMaxPreasureRecursive(voyagers);
        return maxPreasure;
    }

    public void computeMaxPreasureRecursive(List<Voyager> voyagers) {
        List<ValveRoom> alreadyVisitedRooms = getAlreadyVisitedRooms(voyagers);
        List<ValveRoom> nextRooms = getRoomsExcluding(alreadyVisitedRooms);

        boolean isComplete = visitAllNextRooms(voyagers, nextRooms);

        if (isComplete) {
            int currentPreasure = Voyager.getPreasure(voyagers);
            if (maxPreasure < currentPreasure) {
                maxPreasure = Integer.max(maxPreasure, currentPreasure);
                Printer.println(maxPreasure);
            }
        }
    }

    private boolean visitAllNextRooms(List<Voyager> voyagers, List<ValveRoom> nextRooms) {
        boolean isComplete = true;
        List<Pair<Voyager, ValveRoom>> cartesianProduct = getCartesianProduct(voyagers, nextRooms);

        List<ValveRoom> takenRooms;
        for (Pair<Voyager, ValveRoom> coupleVVR1 : cartesianProduct) {
            Voyager voyager1 = coupleVVR1.getLeft();
            ValveRoom room1 = coupleVVR1.getRight();
            takenRooms = new ArrayList<>();
            if(takenRooms.contains(room1)) {
                continue;
            } else {
                takenRooms.add(room1);
            }
            List<Pair<Voyager, ValveRoom>> subCartesianProduct = getSubCartesianProduct(cartesianProduct, voyager1);
            int newTimeBeforeBoom1 = newTimeAfterMoveAndOpen(voyager1.getTimeLeft(), voyager1.getLastRoom(), room1);

            if (newTimeBeforeBoom1 > 0) {
                for (Pair<Voyager, ValveRoom> coupleVVR2 : subCartesianProduct) {
                    List<Voyager> copyOfVoyagers = Voyager.copyList(voyagers);
                    Voyager voyager2 = coupleVVR2.getLeft();
                    ValveRoom room2 = coupleVVR2.getRight();
                    if(takenRooms.contains(room2)) {
                        continue;
                    } else {
                        takenRooms.add(room2);
                    }
                    int newTimeBeforeBoom2 = newTimeAfterMoveAndOpen(voyager2.getTimeLeft(), voyager2.getLastRoom(), room2);

                    if (newTimeBeforeBoom2 > 0) {
                        ValveRoom nextRoom1 = visitNextRoom(room1.getName(), newTimeBeforeBoom1);
                        ValveRoom nextRoom2 = visitNextRoom(room2.getName(), newTimeBeforeBoom2);
                        copyOfVoyagers.get(0).addRoom(nextRoom1);
                        copyOfVoyagers.get(1).addRoom(nextRoom2);
                        computeMaxPreasureRecursive(copyOfVoyagers);
                        isComplete = false;
                    }
                }
            }
        }
        return isComplete;
    }

    private static List<Pair<Voyager, ValveRoom>> getCartesianProduct(List<Voyager> voyagers, List<ValveRoom> nextRooms) {
        List<Pair<Voyager, ValveRoom>> cartesianProduct = new ArrayList<>();
        for (Voyager voyager : voyagers) {
            for (ValveRoom nextRoom : nextRooms) {
                Pair<Voyager, ValveRoom> newPair = new ImmutablePair<>(voyager, nextRoom);
                cartesianProduct.add(newPair);
            }
        }
        return cartesianProduct;
    }

    private static List<Pair<Voyager, ValveRoom>> getSubCartesianProduct(List<Pair<Voyager, ValveRoom>> cartesianProduct, Voyager voyager) {
        return cartesianProduct.stream()
                .filter(pair -> pair.getLeft() != voyager)
                .collect(Collectors.toList());
    }

    private List<ValveRoom> getAlreadyVisitedRooms(List<Voyager> voyagers) {
        List<ValveRoom> alreadyVisitedRooms = new ArrayList<>();
        for (Voyager voyager : voyagers) {
            alreadyVisitedRooms.addAll(voyager.getVisitedRooms());
        }
        return alreadyVisitedRooms;
    }

    private ValveRoom visitNextRoom(String nextRoomName, int newTimeBeforeBoom) {
        return new ValveRoom(getValveRoom(nextRoomName), newTimeBeforeBoom);
    }

    private int newTimeAfterMoveAndOpen(int timeBeforeBoom, ValveRoom currentRoom, ValveRoom nextRoom) {
        return timeBeforeBoom - getTimeToMoveAndOpen(currentRoom, nextRoom);
    }

    private int getTimeToMoveAndOpen(ValveRoom currentRoom, ValveRoom nextRoom) {
        return currentRoom.getRoomDistance(nextRoom.getName()) + TIME_TO_OPEN;
    }

    private List<ValveRoom> getRoomsExcluding(List<ValveRoom> alreadyVisitedRooms) {
        return valveRooms.values().stream()
                .filter(room -> valveRooms.get(room.getName()).getFlow() > 0)
                .filter(room -> !ValveRoom.containsRoomName(alreadyVisitedRooms, room.getName()))
                .collect(Collectors.toList());
    }
}
