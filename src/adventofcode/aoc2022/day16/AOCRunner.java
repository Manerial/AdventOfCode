package adventofcode.aoc2022.day16;

import template.AOC;
import utilities.FileLoader;
import utilities.Printer;
import utilities.Timer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AdventOfCode 2022 day 16's instructions are <a href="https://adventofcode.com/2022/day/16">here</a>
 */
public class AOCRunner implements AOC {
    private Volcano volcano;

    @Override
    public void run(String file) {
        try {
            Timer timer = new Timer();
            List<String> list = FileLoader.readListFromFile(file);
            initVolcano(list);

            ValveRoom startRoom = volcano.getValveRoom("AA");
            startRoom.setOpenTime(30);

            Integer maxPresure = volcano.getMaxPressure(startRoom, 1);
            Printer.println("Solution 1 : " + maxPresure);
            timer.time();

            // Will run in 26 minutes (quite long...)
            startRoom.setOpenTime(26);
            maxPresure = volcano.getMaxPressure(startRoom, 2);
            Printer.println("Solution 2 : " + maxPresure);
            timer.time();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set default graph to the Volcano
     *
     * @param list The input to parse
     */
    private void initVolcano(List<String> list) {
        volcano = new Volcano();
        list.stream().map(AOCRunner::cleanInput).forEach(this::addRoom);
        volcano.computeDistances();
    }

    /**
     * Clear the input so we can have something more useful
     * @param input : Something like -> alve AA has flow rate=0; tunnels lead to valves DD, II, BB
     * @return : Something like -> AA;O;DD,II,BB
     */
    private static String cleanInput(String input) {
        return input
                .replace("Valve ", "")
                .replace(" has flow rate=", ";")
                .replace("s ", " ")
                .replace(" tunnel lead to valve ", "")
                .replace(", ", ",");
    }

    /**
     * Turn an input string into a new room
     * @param input : Something like -> AA;O;DD,II,BB
     */
    private void addRoom(String input) {
        String[] params = input.split(";");
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
