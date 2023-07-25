package aoc.exercises.year2016.day04;

import org.apache.commons.lang3.StringUtils;
import utilities.AbstractInputParser;
import utilities.errors.NotAcceptedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<List<RoomData>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<RoomData> parseInput() {
        List<RoomData> realRooms = new ArrayList<>();
        for (String line : inputList) {
            RoomData roomData = extractRoomData(line);
            if (roomData.isReal()) {
                realRooms.add(roomData);
            }
        }
        return realRooms;
    }

    private RoomData extractRoomData(String line) {
        Pattern patternAfterName = Pattern.compile("\\d+.+");
        Matcher matcherAfterName = patternAfterName.matcher(line);
        if (matcherAfterName.find()) {
            String afterName = matcherAfterName.group();
            String name = StringUtils.removeEnd(line, afterName);
            String[] split = afterName.split("\\[");
            int id = Integer.parseInt(split[0]);
            String checksum = StringUtils.removeEnd(split[1], "]");
            return new RoomData(id, name, checksum);
        }
        throw new NotAcceptedValue(line);
    }
}
