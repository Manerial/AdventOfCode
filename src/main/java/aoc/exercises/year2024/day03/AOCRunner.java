package aoc.exercises.year2024.day03;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2024/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        String line = inputList.get(0);
        List<String> commands = extractCommands(line);
        solution1 = executeCommands(commands);
        solution2 = executeCommandsWithDo(commands);
    }

    private int executeCommands(List<String> commands) {
        return commands.stream()
                .mapToInt(this::executeCommand)
                .sum();
    }

    private int executeCommandsWithDo(List<String> commands) {
        int result = 0;
        boolean doFlag = true;
        for (String command : commands) {
            if (command.equals("do()")) {
                doFlag = true;
            } else if (command.equals("don't()")) {
                doFlag = false;
            } else if (doFlag) {
                result += executeCommand(command);
            }
        }
        return result;
    }

    private int executeCommand(String command) {
        Pattern pattern = Pattern.compile("(\\d+,\\d+)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) {
            return 0;
        }
        String multiplication = matcher.group(1);
        String[] numbers = multiplication.split(",");
        return Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
    }

    private List<String> extractCommands(String line) {
        List<String> commands = new ArrayList<>();
        Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String command = matcher.group(1);
            commands.add(command);
        }

        return commands;
    }
}