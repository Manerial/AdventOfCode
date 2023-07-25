package aoc.exercises.year2015.day06;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser extends AbstractInputParser<List<FromToSwitch>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<FromToSwitch> parseInput() {
        return inputList.stream().map(input -> {
            Pattern numbersPattern = Pattern.compile("(\\d*,\\d*)\\D*(\\d*,\\d*)");
            Matcher match = numbersPattern.matcher(input);
            if (!match.find()) {
                throw new IllegalArgumentException(input);
            }
            Position from = new Position(match.group(1), ",");
            Position to = new Position(match.group(2), ",");

            String switchLightStr = input.split(" ")[1];
            SwitchLight switchLight;
            switch (switchLightStr) {
                case "on" -> switchLight = SwitchLight.ON;
                case "off" -> switchLight = SwitchLight.OFF;
                default -> switchLight = SwitchLight.TOGGLE;
            }

            return new FromToSwitch(from, to, switchLight);
        }).toList();
    }
}
