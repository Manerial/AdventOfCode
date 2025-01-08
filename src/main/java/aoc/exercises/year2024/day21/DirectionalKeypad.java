package aoc.exercises.year2024.day21;

import aoc.common_objects.Position;

import java.util.ArrayList;
import java.util.List;

public class DirectionalKeypad extends Keypad {
    public DirectionalKeypad() {
        super();
        keymap.put('<', new Position(0, 1));
        keymap.put('v', new Position(1, 1));
        keymap.put('>', new Position(2, 1));
        keymap.put('^', new Position(1, 0));
        keymap.put('A', new Position(2, 0));
        forbidenPosition = new Position(0, 0);
    }


    public List<Character> getLineDirections(List<Character> line) {
        List<Character> path = new ArrayList<>();
        Position currentPosition = new Position(keymap.get('A'));
        for (char c : line) {
            path.addAll(goToThenPress(c, currentPosition));
        }
        return path;
    }

    private List<Character> goToThenPress(char target, Position currentPosition) {
        Position targetPosition = keymap.get(target);
        List<Position> path = Position.pathTo(currentPosition, targetPosition, false);
        List<Character> directionPath = getDirections(path);
        currentPosition.setPosition(targetPosition);
        return directionPath;
    }
}
