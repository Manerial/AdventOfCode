package adventofcode.aoc2022.day16;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;

import java.io.IOException;
import java.util.*;

/**
 * AdventOfCode 2022 day 15's instructions are <a href="https://adventofcode.com/2022/day/15">here</a>
 */
public class AOCRunner implements AOC {
    private Volcano volcano;

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            initVolcano(list);
            Integer maxPresure = getMaxPresure("AA", 30, 1);
            Printer.println("Solution 1 : " + maxPresure);

            Integer maxPresureWithElephants = getMaxPresure("AA", 26, 2);
            Printer.println("Solution 2 : " + maxPresureWithElephants);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer getMaxPresure(String startPosition, int timeBeforeBoom, int groups) {
        List<Integer> searchOrderResults = new ArrayList<>();
        List<String> rooms = new ArrayList<>(volcano.getFlowMapForCurrentRoom(timeBeforeBoom, startPosition).keySet());
        List<SearchOrder> searchOrders = getSearchOrders(rooms, new SearchOrder());
        for (SearchOrder searchOrder : searchOrders) {
                searchOrderResults.add(volcano.search(searchOrder.getOrder(), startPosition, timeBeforeBoom));
        }
        return searchOrderResults.stream().reduce(Integer::max).orElse(0);
    }

    private List<SearchOrder> getSearchOrders(List<String> rooms, SearchOrder construction) {
        List<SearchOrder> searchOrders = new ArrayList<>();
        boolean isComplete = true;
        for (String room : rooms) {
            if (!construction.contains(room)) {
                SearchOrder newSearchOrder = new SearchOrder(construction);
                newSearchOrder.addOrder(room);
                searchOrders.addAll(getSearchOrders(rooms, newSearchOrder));
                isComplete = false;
            }
        }
        if (isComplete) {
            searchOrders.add(construction);
        }

        return searchOrders;
    }


    private void initVolcano(List<String> list) {
        volcano = new Volcano();
        list.stream().map(AOCRunner::cleanInput)
                .forEach(this::addRoom);
        volcano.computeDistances();
    }

    // Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
    private static String cleanInput(String s) {
        return s.replace("Valve ", "")
                .replace(" has flow rate=", ";")
                .replace("s ", " ")
                .replace(" tunnel lead to valve ", "")
                .replace(", ", ",");
    }

    private void addRoom(String s) {
        String[] params = s.split(";");
        String name = params[0];
        int flow = Integer.parseInt(params[1]);
        Map<String, Integer> connectedRooms = new HashMap<>();
        for (String connectedRoomsStr : params[2].split(",")) {
            connectedRooms.put(connectedRoomsStr, 1);
        }
        ValveRoom valveRoom = new ValveRoom(name, flow, connectedRooms);
        volcano.addValveRoom(name, valveRoom);
    }
}
