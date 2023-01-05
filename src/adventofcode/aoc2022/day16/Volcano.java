package adventofcode.aoc2022.day16;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Volcano {
    private static final int TIME_TO_OPEN = 1;
    private final Map<String, ValveRoom> valveRooms;
    private int maxPressure;

    public Volcano() {
        valveRooms = new HashMap<>();
    }

    public ValveRoom getValveRoom(String aa) {
        return valveRooms.get(aa);
    }

    public void addValveRoom(String name, ValveRoom valveRoom) {
        this.valveRooms.put(name, valveRoom);
    }

    /**
     * For each room, get the distance to each different rooms
     */
    public void computeDistances() {
        for (ValveRoom valveRoom : this.valveRooms.values()) {
            recursiveSetDistance(valveRoom.getName(), valveRoom, 0);
        }
    }

    /**
     * For each room directly connected, get each other rooms and set distance + 1
     * @param roomFrom : The room we are currently parsing
     * @param roomTo : The next room
     * @param distance : The distance to set if inferior to previous one
     */
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

    /**
     * For a starting room and a number of groups, parse the whole graph to find the best pressure we can possibly have
     * @param startRoom : The room we shall start
     * @param groups : The number of groups
     * @return : The max pressure possible
     */
    public Integer getMaxPressure(ValveRoom startRoom, int groups) {
        List<Voyager> voyagers = new ArrayList<>();
        for (int group = 0; group < groups; group++) {
            Voyager voyager = new Voyager(group, startRoom);
            voyagers.add(voyager);
        }
        maxPressure = 0;
        computeMaxPreassure(voyagers);
        return maxPressure;
    }

    public void computeMaxPreassure(List<Voyager> voyagers) {
        List<ValveRoom> nextRooms = getRoomsExcludingAlreadyVisited(voyagers);
        if (isMaxPressure(voyagers, nextRooms)) {
            return;
        }
        List<Pair<Voyager, ValveRoom>> cartesianProduct = getCartesianProduct(voyagers, nextRooms);
        boolean isComplete = nextRooms.isEmpty() || visitAllRoomsWithAllGroups(cartesianProduct, Voyager.copyList(voyagers), new ArrayList<>());
        if (isComplete) {
            int currentPreassure = Voyager.getPreassure(voyagers);
            maxPressure = Integer.max(maxPressure, currentPreassure);
        }
    }

    private boolean visitAllRoomsWithAllGroups(List<Pair<Voyager, ValveRoom>> cartesianProduct, List<Voyager> voyagers, List<ValveRoom> takenRooms) {
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
                    visitAllRoomsWithAllGroups(subCartesianProduct, newVoyagers, newTakenRooms);
                } else {
                    computeMaxPreassure(newVoyagers);
                }
                isComplete = false;
            }
        }
        return isComplete;
    }

    private boolean isMaxPressure(List<Voyager> voyagers, List<ValveRoom> nextRooms) {
        int currentPressure = Voyager.getPreassure(voyagers);
        int maxTime = voyagers.stream().map(Voyager::getTimeLeft).reduce(Integer::max).orElse(0);
        int possibleMax = nextRooms.stream().map(room -> room.getFlow() * maxTime).reduce(Integer::sum).orElse(0);
        return currentPressure + possibleMax <= maxPressure;
    }

    /**
     * Combine all the possibilities
     * @param voyagers : the first list to combine
     * @param nextRooms : the second list to combine
     */
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

    /**
     * Get a new cartesian product excluding a value
     * @param cartesianProduct : the map to reduce
     * @param voyager : the value to remove
     * @return : cartesianProduct excluding voyager
     */
    private static List<Pair<Voyager, ValveRoom>> getSubCartesianProduct(List<Pair<Voyager, ValveRoom>> cartesianProduct, Voyager voyager) {
        return cartesianProduct.stream()
                .filter(pair -> pair.getLeft() != voyager)
                .collect(Collectors.toList());
    }

    private List<String> getAlreadyVisitedRooms(List<Voyager> voyagers) {
        Set<String> alreadyVisitedRooms = new HashSet<>();
        for (Voyager voyager : voyagers) {
            List<String> roomNames = voyager.getVisitedRooms().stream().map(ValveRoom::getName).collect(Collectors.toList());
            alreadyVisitedRooms.addAll(roomNames);
        }
        return new ArrayList<>(alreadyVisitedRooms);
    }

    private ValveRoom visitNextRoom(String nextRoomName, int newTimeBeforeBoom) {
        return new ValveRoom(getValveRoom(nextRoomName), newTimeBeforeBoom);
    }

    private int newTimeAfterMoveAndOpen(int timeBeforeBoom, ValveRoom currentRoom, ValveRoom nextRoom) {
        return timeBeforeBoom - (currentRoom.getRoomDistance(nextRoom.getName()) + TIME_TO_OPEN);
    }

    /**
     * Get all the rooms with a flow and not in the list
     * @param voyagers : The list of voyagers (contains rooms to exclude from path)
     * @return : the list of rooms with a flow that are not in the list
     */
    private List<ValveRoom> getRoomsExcludingAlreadyVisited(List<Voyager> voyagers) {
        List<String> exclusion = getAlreadyVisitedRooms(voyagers);
        return valveRooms.values().stream()
                .filter(room -> valveRooms.get(room.getName()).getFlow() > 0)
                .filter(room -> !ValveRoom.containsRoomName(exclusion, room.getName()))
                .collect(Collectors.toList());
    }

    private void setVoyagerNextRoom(List<Voyager> voyagers, Voyager voyager, ValveRoom nextRoom) {
        voyagers.stream()
                .filter(voyager1 -> voyager1.getId() == voyager.getId())
                .findFirst()
                .ifPresent(value -> value.addRoom(nextRoom));
    }
}
