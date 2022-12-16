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

            List<String> rooms = new ArrayList<>(volcano.getFlowMapForCurrentRoom(30, "AA").keySet());
            Printer.println(volcano.getFlowMapForCurrentRoom(30, "AA"));

            List<SearchOrder> searchOrders = volcano.getSearchOrders(rooms, new SearchOrder());

            Integer maxPresure = getMaxPresure(searchOrders, "AA", 30, 1);
            Printer.println("Solution 1 : " + maxPresure);

            Integer maxPresureWithElephants = getMaxPresure(searchOrders, "AA", 26, 2);
            Printer.println("Solution 2 : " + maxPresureWithElephants);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Integer getMaxPresure(List<SearchOrder> searchOrders, String startPosition, int timeBeforeBoom, int groups) {
        List<Integer> searchOrderResults = new ArrayList<>();
        for (SearchOrder searchOrder : searchOrders) {
            addResultByGroups(startPosition, timeBeforeBoom, groups, searchOrderResults, searchOrder);
        }
        return searchOrderResults.stream().reduce(Integer::max).orElse(0);
    }

    private void addResultByGroups(String startPosition, int timeBeforeBoom, int groups, List<Integer> searchOrderResults, SearchOrder searchOrder) {
        for (List<SearchOrder> searchOrderGrouped : splitSearchOrder(searchOrder, groups)) {
            int resultByGroup = 0;
            for (SearchOrder searchOrderGroup : searchOrderGrouped) {
                resultByGroup += volcano.search(searchOrderGroup.getOrder(), startPosition, timeBeforeBoom);
            }
            searchOrderResults.add(resultByGroup);
        }
    }

    private List<List<SearchOrder>> splitSearchOrder(SearchOrder searchOrder, int groups) {
        List<List<SearchOrder>> result = new ArrayList<>();
        for(int index = 0; index < searchOrder.getOrder().size(); index++) {
            List<SearchOrder> resultByGroup = new ArrayList<>();

            if(groups == 1) {
                resultByGroup.add(searchOrder);
            }
            if(groups == 2) {
                SearchOrder searchOrder1 = new SearchOrder(searchOrder.getOrder().subList(0, index));
                SearchOrder searchOrder2 = new SearchOrder(searchOrder.getOrder().subList(index, searchOrder.getOrder().size()));
                resultByGroup.add(searchOrder1);
                resultByGroup.add(searchOrder2);
            }
            result.add(resultByGroup);
        }
        return result;
    }
}
