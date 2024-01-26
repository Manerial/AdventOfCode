package aoc.exercises.year2022.day22;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import utilities.errors.NotAcceptedValue;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {
    private final Map<Position, Character> boardMap = new HashMap<>();
    private final List<Pair<Integer, String>> moves = new ArrayList<>();
    @Getter
    private Position position;
    private Position previousPosition;
    private Position nextPosition;
    private Direction direction = Direction.EAST;
    @Setter
    private Cube cube;
    private boolean useCube = false;

    public void add(Position position, char c) {
        boardMap.put(position, c);
    }

    public void setMoves(String movesToParse) {
        String[] steps = movesToParse.split("\\D");
        String[] turns = movesToParse.split("\\d+");
        for (int i = 0; i < steps.length - 1; i++) {
            int step = Integer.parseInt(steps[i]);
            String turn = turns[i + 1];
            moves.add(Pair.of(step, turn));
        }

        int step = Integer.parseInt(steps[steps.length - 1]);
        moves.add(Pair.of(step, null));
    }

    public void useCube(boolean useCube) {
        this.useCube = useCube;
    }

    public Set<Position> getPositions() {
        return boardMap.keySet();
    }

    public void resetPosition() {
        int row = 1;
        int col = boardMap.keySet().stream()
                .filter(p -> p.getY() == 1)
                .mapToInt(Position::getX)
                .min()
                .orElseThrow();
        this.position = new Position(col, row);
    }

    public void moveThrough() {
        for (Pair<Integer, String> move : moves) {
            int steps = move.getKey();
            move(steps);
            String turn = move.getValue();
            if (turn != null) {
                switch (turn) {
                    case "L" -> direction = direction.turnLeft();
                    case "R" -> direction = direction.turnRight();
                    default -> throw new NotAcceptedValue(turn);
                }
            }
        }
    }

    private void move(int steps) {
        Direction nextDirection = direction;
        for (int j = 0; j < steps; j++) {
            previousPosition = new Position(position);
            nextPosition = new Position(position);
            switch (direction) {
                case EAST -> {
                    nextPosition.incX();
                    previousPosition.decX();
                }
                case SOUTH -> {
                    nextPosition.incY();
                    previousPosition.decY();
                }
                case WEST -> {
                    nextPosition.decX();
                    previousPosition.incX();
                }
                case NORTH -> {
                    nextPosition.decY();
                    previousPosition.incY();
                }
                default -> throw new NotAcceptedValue(direction);
            }
            if (!boardMap.containsKey(nextPosition)) {
                if (!useCube) {
                    warp(nextPosition);
                } else {
                    nextPosition = cube.warp(position, previousPosition);
                    nextDirection = cube.turn(position, nextPosition, direction);
                }
            }
            if (boardMap.get(nextPosition) == '#') {
                return;
            } else {
                position = nextPosition;
                direction = nextDirection;
            }
        }
    }

    private void warp(Position nextPosition) {
        Set<Position> positionsX = boardMap.keySet().stream().filter(p -> p.getY() == nextPosition.getY()).collect(Collectors.toSet());
        Set<Position> positionsY = boardMap.keySet().stream().filter(p -> p.getX() == nextPosition.getX()).collect(Collectors.toSet());

        int minX = positionsX.stream().mapToInt(Position::getX).min().orElse(-1);
        int maxX = positionsX.stream().mapToInt(Position::getX).max().orElse(-1);
        int minY = positionsY.stream().mapToInt(Position::getY).min().orElse(-1);
        int maxY = positionsY.stream().mapToInt(Position::getY).max().orElse(-1);

        switch (direction) {
            case EAST -> {
                if (nextPosition.getX() > maxX) {
                    nextPosition.setX(minX);
                }
            }
            case SOUTH -> {
                if (nextPosition.getY() > maxY) {
                    nextPosition.setY(minY);
                }
            }
            case WEST -> {
                if (nextPosition.getX() < minX) {
                    nextPosition.setX(maxX);
                }
            }
            case NORTH -> {
                if (nextPosition.getY() < minY) {
                    nextPosition.setY(maxY);
                }
            }
            default -> throw new NotAcceptedValue(direction);
        }
    }

    private int getOrdinalDirection() {
        int result;
        switch (direction) {
            case EAST -> result = 0;
            case SOUTH -> result = 1;
            case WEST -> result = 2;
            case NORTH -> result = 3;
            default -> throw new NotAcceptedValue(direction);
        }
        return result;
    }

    public int getFinalPassword() {
        return position.getY() * 1000 + position.getX() * 4 + getOrdinalDirection();
    }
}
