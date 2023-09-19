package aoc.exercises.year2022.day16;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputParser extends AbstractInputParser<ValveRoom[]> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public ValveRoom[] parseInput() {
        cleanAndSortInput();
        ValveRoom[] valveRooms = parseValveRooms();
        connectAllRooms(valveRooms);

        ValveRoom[] valveRoomsWithFlow = Arrays.stream(valveRooms)
                .filter(valveRoom -> valveRoom.getFlow() > 0 || valveRoom.getIndex() == 0)
                .toArray(ValveRoom[]::new);

        cleanUnusedRooms(valveRoomsWithFlow);
        rebindIndex(valveRoomsWithFlow);
        return valveRoomsWithFlow;
    }

    private void cleanAndSortInput() {
        inputList = inputList.stream()
                .map(InputParser::cleanInput)
                .sorted()
                .toList();

        String firstLine = inputList.get(0);
        inputList = inputList.stream()
                .sorted((o1, o2) -> Integer.parseInt(o2.split(";")[1]) - Integer.parseInt(o1.split(";")[1]))
                .collect(Collectors.toList());
        inputList.remove(firstLine);
        inputList.add(0, firstLine);

        for (int i = 0; i < inputList.size(); i++) {
            String roomName = inputList.get(i).split(";")[0];
            int finalI = i;
            inputList = inputList.stream()
                    .map(s -> s.replace(roomName, String.valueOf(finalI)))
                    .toList();
        }
    }

    /**
     * Clear the line so we can have something more useful
     *
     * @param line : Something like -> alve AA has flow rate=0; tunnels lead to valves DD, II, BB
     * @return : Something like -> AA;O;DD,II,BB
     */
    private static String cleanInput(String line) {
        return line
                .replace("Valve ", "")
                .replace(" has flow rate=", ";")
                .replace("s ", " ")
                .replace(" tunnel lead to valve ", "")
                .replace(", ", ",");
    }

    private ValveRoom[] parseValveRooms() {
        ValveRoom[] valveRooms = new ValveRoom[inputList.size()];
        for (int i = 0; i < inputList.size(); i++) {
            String line = inputList.get(i);
            valveRooms[i] = toValveRoom(line);
        }
        return valveRooms;
    }

    /**
     * Turn an input string into a new room
     *
     * @param input : Something like -> AA;O;DD,II,BB
     */
    private ValveRoom toValveRoom(String input) {
        String[] params = input.split(";");
        int index = Integer.parseInt(params[0]);
        int flow = Integer.parseInt(params[1]);
        String connectedRoomsStr = params[2];
        Map<Integer, Integer> connectedRooms = toConnectedRoomsMap(connectedRoomsStr);
        connectedRooms.put(index, 0);
        return new ValveRoom(index, flow, connectedRooms);
    }

    private Map<Integer, Integer> toConnectedRoomsMap(String connectedRoomsStr) {
        Map<Integer, Integer> connectedRooms = new HashMap<>();
        for (String indexStr : connectedRoomsStr.split(",")) {
            int roomIndex = Integer.parseInt(indexStr);
            connectedRooms.put(roomIndex, 1);
        }
        return connectedRooms;
    }

    private void connectAllRooms(ValveRoom[] rooms) {
        int maxDistance = rooms.length;

        for (ValveRoom k : rooms) {
            for (ValveRoom i : rooms) {
                for (ValveRoom j : rooms) {
                    int distanceIJ = i.getRoomDistanceByIndexOrDefault(j.getIndex(), maxDistance);
                    int distanceIK = i.getRoomDistanceByIndexOrDefault(k.getIndex(), maxDistance);
                    int distanceKJ = k.getRoomDistanceByIndexOrDefault(j.getIndex(), maxDistance);
                    int distance = Math.min(distanceIJ, distanceIK + distanceKJ);

                    i.updateDistance(j.getIndex(), distance);
                }
            }
        }
    }

    private void cleanUnusedRooms(ValveRoom[] usedRooms) {
        Integer[] usedRoomsIndex = Arrays.stream(usedRooms).map(ValveRoom::getIndex).toArray(Integer[]::new);
        for (ValveRoom valveRoom : usedRooms) {
            valveRoom.keepOnly(usedRoomsIndex);
        }
    }

    private void rebindIndex(ValveRoom[] valveRoomsToRebind) {
        for (int newIndex = 0; newIndex < valveRoomsToRebind.length; newIndex++) {
            ValveRoom valveRoom = valveRoomsToRebind[newIndex];
            int oldIndex = valveRoom.getIndex();
            valveRoom.setIndex(newIndex);
            int finalNewIndex = newIndex;
            Arrays.stream(valveRoomsToRebind).forEach(valveRoomConnected -> {
                int distance = valveRoomConnected.getRoomDistanceByIndex(oldIndex);
                valveRoomConnected.getRoomDistanceByIndex().remove(oldIndex);
                valveRoomConnected.getRoomDistanceByIndex().put(finalNewIndex, distance);
            });
        }
    }
}
