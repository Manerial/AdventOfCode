package aoc.exercises.year2016.day08;

import aoc.common_objects.Position;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utilities.ResourceIO.DELIMITER;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Map<Position, String> screen;
    private static final int LINES = 6;
    private static final int COLUMNS = 50;

    @Override
    public void run() {
        screen = new HashMap<>();
        createRectangle(COLUMNS, LINES, " ");
        for (String line : inputList) {
            operate(line);
        }
        solution1 = screen.values().stream().filter(s -> s.equals("#")).count();
        solution2 = printScreen();
    }

    private String printScreen() {
        StringBuilder sb = new StringBuilder();
        for (int line = 0; line < LINES; line++) {
            for (int column = 0; column < COLUMNS; column++) {
                Position position = new Position(column, line);
                sb.append(screen.get(position));
            }
            sb.append(DELIMITER);
        }
        return sb.toString();
    }

    private void operate(String command) {
        if (command.contains("rect")) {
            rectangle(command);
        } else {
            rotate(command);
        }
    }

    private void rectangle(String command) {
        Pattern pattern = Pattern.compile("(\\d+)x(\\d+)");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            int columns = Integer.parseInt(matcher.group(1));
            int lines = Integer.parseInt(matcher.group(2));
            createRectangle(columns, lines, "#");
        }
    }

    private void createRectangle(int columns, int lines, String pixel) {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                Position position = new Position(column, line);
                screen.put(position, pixel);
            }
        }
    }

    private void rotate(String command) {
        Pattern pattern = Pattern.compile("(\\d+) by (\\d+)");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            int identity = Integer.parseInt(matcher.group(1));
            int offset = Integer.parseInt(matcher.group(2));
            if (command.contains("column")) {
                rotateColumn(identity, offset);
            } else {
                rotateLine(identity, offset);
            }
        }
    }

    private void rotateColumn(int column, int offset) {
        List<String> screenColumn = new ArrayList<>();
        for (int line = 0; line < LINES; line++) {
            Position position = new Position(column, line);
            screenColumn.add(screen.get(position));
        }

        for (int line = 0; line < LINES; line++) {
            Position position = new Position(column, (line + offset) % LINES);
            screen.put(position, screenColumn.get(line));
        }
    }

    private void rotateLine(int line, int offset) {
        List<String> screenLine = new ArrayList<>();
        for (int column = 0; column < COLUMNS; column++) {
            Position position = new Position(column, line);
            screenLine.add(screen.get(position));
        }

        for (int column = 0; column < COLUMNS; column++) {
            Position position = new Position((column + offset) % COLUMNS, line);
            screen.put(position, screenLine.get(column));
        }
    }
}
