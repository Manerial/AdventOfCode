package adventofcode.aoc2022.day16;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private final Map<String, ValveRoom> valveRooms;
    private int maxPreasure;

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

    public Integer getMaxPresure(ValveRoom startRoom, int groups) {
        List<Voyager> voyagers = new ArrayList<>();
        for (int group = 0; group < groups; group++) {
            Voyager voyager = new Voyager(group, startRoom);
            voyagers.add(voyager);
        }
        maxPreasure = 0;
        computeMaxPreasureRecursive(voyagers);
        return maxPreasure;
    }

    public void computeMaxPreasureRecursive(List<Voyager> voyagers) {
        List<ValveRoom> alreadyVisitedRooms = getAlreadyVisitedRooms(voyagers);
        List<ValveRoom> nextRooms = getRoomsExcluding(alreadyVisitedRooms);


        boolean isComplete = nextRooms.isEmpty() || visitAllNextRooms(voyagers, nextRooms);
        if (isComplete) {
            int currentPreasure = Voyager.getPreasure(voyagers);
            maxPreasure = Integer.max(maxPreasure, currentPreasure);
        }
    }

    private boolean visitAllNextRooms(List<Voyager> voyagers, List<ValveRoom> nextRooms) {
        List<Pair<Voyager, ValveRoom>> cartesianProduct = getCartesianProduct(voyagers, nextRooms);
        return test(cartesianProduct, Voyager.copyList(voyagers), new ArrayList<>());
    }

    private boolean test(List<Pair<Voyager, ValveRoom>> cartesianProduct, List<Voyager> voyagers, List<ValveRoom> takenRooms) {
        boolean isComplete = true;
        for (Pair<Voyager, ValveRoom> coupleVVR : cartesianProduct) {
            Voyager voyager = coupleVVR.getLeft();
            ValveRoom room = coupleVVR.getRight();
            int newTimeBeforeBoom = newTimeAfterMoveAndOpen(voyager.getTimeLeft(), voyager.getLastRoom(), room);
            List<Pair<Voyager, ValveRoom>> subCartesianProduct = getSubCartesianProduct(cartesianProduct, voyager);
            if (!takenRooms.contains(room) && newTimeBeforeBoom >= 0) {
                List<ValveRoom> newTakenRooms = ValveRoom.copyList(takenRooms);
                newTakenRooms.add(room);
                ValveRoom nextRoom = visitNextRoom(room.getName(), newTimeBeforeBoom);
                List<Voyager> newVoyagers = Voyager.copyList(voyagers);
                setVoyagerNextRoom(newVoyagers, voyager, nextRoom);
                if (!subCartesianProduct.isEmpty()) {
                    test(subCartesianProduct, newVoyagers, newTakenRooms);
                } else {
                    computeMaxPreasureRecursive(newVoyagers);
                }
                isComplete = false;
            }
        }
        return isComplete;
    }

    private static void setVoyagerNextRoom(List<Voyager> voyagers, Voyager voyager, ValveRoom nextRoom) {
        Voyager inListVoyager = voyagers.stream().filter(voyager1 -> voyager1.getId() == voyager.getId()).findFirst().orElse(null);
        if (inListVoyager == null) {
            throw new NullPointerException();
        }
        inListVoyager.addRoom(nextRoom);
    }

    private static List<Pair<Voyager, ValveRoom>> getCartesianProduct(List<Voyager> voyagers, List<ValveRoom> nextRooms) {
        List<Pair<Voyager, ValveRoom>> cartesianProduct = new ArrayList<>();
        for (Voyager voyager : voyagers) {
            for (ValveRoom nextRoom : nextRooms) {
                Pair<Voyager, ValveRoom> newPair = new ImmutablePair<>(voyager, nextRoom);
                cartesianProduct.add(newPair);
            }
        }
        cartesianProduct.sort(Comparator.comparingInt(o -> o.getRight().getFlow()));
        cartesianProduct.sort(Comparator.comparingInt(o -> o.getLeft().getId()));
        cartesianProduct.sort(Comparator.comparingInt(o -> o.getLeft().getTimeLeft()));
        Collections.reverse(cartesianProduct);
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
