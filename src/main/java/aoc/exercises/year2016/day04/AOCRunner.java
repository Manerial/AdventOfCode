package aoc.exercises.year2016.day04;

import utilities.AbstractAOC;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<RoomData> realRooms = inputParser.parseInput();
        solution1 = realRooms.stream()
                .mapToInt(RoomData::getId)
                .sum();
        solution2 = realRooms.stream()
                .filter(roomData -> roomData.uncypherRoomName().contains("northpole"))
                .map(RoomData::getId)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    if (list.isEmpty()) {
                        throw new NoSuchElementException("No rooms are related to the North Pole");
                    } else if (list.size() > 1) {
                        throw new IllegalArgumentException("Too many elements related to the North Pole");
                    }
                    return list.get(0);
                }));
    }
}