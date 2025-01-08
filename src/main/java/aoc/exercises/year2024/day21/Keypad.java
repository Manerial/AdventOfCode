package aoc.exercises.year2024.day21;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;

import java.util.*;

public abstract class Keypad {
    protected final Map<Character, Position> keymap;
    protected final Map<String, Long> movesByKeyPair;
    protected Position forbidenPosition;

    protected Keypad() {
        this.keymap = new HashMap<>();
        this.movesByKeyPair = new HashMap<>();
    }

    public void setMovesByKeyPair(Keypad connectedKeypad) {
        Set<Character> keys = keymap.keySet();
        for (Character from : keys) {
            for (Character to : keys) {
                // for each pair of keys
                long movesForKeyPair = getMovesForKeyPair(connectedKeypad, keymap.get(from), keymap.get(to));
                movesByKeyPair.put("" + from + to, movesForKeyPair);
            }
        }
    }

    private long getMovesForKeyPair(Keypad connectedKeypad, Position from, Position to) {
        List<Character> directionPath = getShortestPath(from, to);
        if (connectedKeypad == null) {
            return directionPath.size();
        } else {
            return getMovesFromConnectedKeypad(directionPath, connectedKeypad);
        }
    }

    private long getMovesFromConnectedKeypad(List<Character> path, Keypad connectedKeypad) {
        long moves = 0;
        path.add(0, 'A');
        for (int i = 0; i < path.size() - 1; i++) {
            char from = path.get(i);
            char to = path.get(i + 1);
            moves += connectedKeypad.movesByKeyPair.get("" + from + to);
        }
        return moves;
    }

    public List<Character> getShortestPath(Position from, Position to) {
        List<Position> preferredPath = Position.pathTo(from, to, true);
        List<Character> directionPath1 = getDirections(preferredPath);

        List<Position> alternativePath = Position.pathTo(from, to, false);
        List<Character> directionPath2 = getDirections(alternativePath);

        if (preferredPath.contains(forbidenPosition)) {
            return directionPath2;
        } else if (alternativePath.contains(forbidenPosition)) {
            return directionPath1;
        }
        if (directionPath1.equals(directionPath2)) {
            return directionPath1;
        }

        return shortestAfter3Levels(directionPath1, directionPath2);
    }

    protected List<Character> shortestAfter3Levels(List<Character> directionPath1, List<Character> directionPath2) {
        DirectionalKeypad keypad = new DirectionalKeypad();
        List<Character> path1 = new ArrayList<>(directionPath1);
        List<Character> path2 = new ArrayList<>(directionPath2);
        int incr = 0;
        while (path1.size() == path2.size() && incr < 3) {
            path1 = keypad.getLineDirections(path1);
            path2 = keypad.getLineDirections(path2);
            incr++;
        }
        return path1.size() <= path2.size() ? directionPath1 : directionPath2;
    }

    protected static List<Character> getDirections(List<Position> path) {
        List<Character> directionPath = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Direction direction = Position.directionFromTo(path.get(i), path.get(i + 1));
            assert direction != null;
            directionPath.add(direction.toChar());
        }
        directionPath.add('A');
        return directionPath;
    }
}