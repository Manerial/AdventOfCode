package aoc.exercises.year2018.day10;

import aoc.common_objects.Position;
import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<Star>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Star> parseInput() {
        List<Star> stars = new ArrayList<>();
        for (String line : inputList) {
            // position=< 9,  1> velocity=< 0,  2>
            line = line.replace("position=<", "")
                    .replace("> velocity=<", ";")
                    .replace(">", "")
                    .replace(" ", "");
            // 9,1;0,2
            String[] parts = line.split(";");
            String[] position = parts[0].split(",");
            String[] velocity = parts[1].split(",");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            int speedX = Integer.parseInt(velocity[0]);
            int speedY = Integer.parseInt(velocity[1]);
            stars.add(new Star(new Position(x, y), new Position(speedX, speedY)));
        }
        return stars;
    }
}
