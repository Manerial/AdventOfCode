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
            Integer maxPresure = getMaxPresure();
            Printer.println("Solution 1 : " + maxPresure);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer getMaxPresure() {
        List<Map<String, Integer>> maps = volcano.getAllFlowMapForCurrentRoom("AA", new HashMap<>(), 30);
        for (Map<String, Integer> map : maps) {
            Printer.println(map);
        }
        Integer maxValue = maps.stream().map(map -> map.values().stream().reduce(Integer::sum).orElse(0)).reduce(Integer::max).orElse(0);
        return maxValue;
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
