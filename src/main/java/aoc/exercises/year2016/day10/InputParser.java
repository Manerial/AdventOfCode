package aoc.exercises.year2016.day10;

import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<Integer, Bot>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Integer, Bot> parseInput() {
        Map<Integer, Bot> bots = new HashMap<>();
        List<String> sortedList = inputList.stream().sorted().toList();
        for (String line : sortedList) {
            if (line.startsWith("bot")) {
                // bot X gives low to DESTINATION Y and high to DESTINATION Z
                parseBot(line, bots);
            } else {
                // value X goes to bot Y
                addChipToBot(line, bots);
            }
        }
        return bots;
    }

    private static void parseBot(String line, Map<Integer, Bot> bots) {
        // bot X gives low to DESTINATION Y and high to DESTINATION Z
        String cleanedLine = line
                .replaceFirst("bot ", "")
                .replace(" gives low to ", ";")
                .replace(" and high to ", ";");
        // X;DESTINATION Y;DESTINATION Z
        String[] parts = cleanedLine.split(";");
        int botId = Integer.parseInt(parts[0]);
        String[] lowDestination = parts[1].split(" ");
        String[] highDestination = parts[2].split(" ");
        Bot bot = new Bot(
                botId,
                lowDestination[0].equals("bot"),
                highDestination[0].equals("bot"),
                Integer.parseInt(lowDestination[1]),
                Integer.parseInt(highDestination[1])
        );
        bots.put(botId, bot);
    }

    private static void addChipToBot(String line, Map<Integer, Bot> bots) {
        // value X goes to bot Y
        String cleanedLine = line
                .replace("value ", "")
                .replace(" goes to bot ", ";");
        // X;Y
        String[] parts = cleanedLine.split(";");
        int botId = Integer.parseInt(parts[1]);
        int value = Integer.parseInt(parts[0]);
        bots.get(botId).takeChip(value);
    }

}
