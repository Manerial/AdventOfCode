package aoc.common_objects;

import utilities.errors.NotAcceptedValue;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;


    public static Direction charToDirection(char direction) {
        return switch (direction) {
            case 'W', 'L', '<' -> WEST;
            case 'E', 'R', '>' -> EAST;
            case 'N', 'U', '^' -> NORTH;
            case 'S', 'D', 'v' -> SOUTH;
            default -> throw new NotAcceptedValue(direction);
        };
    }

    public Direction turnLR(String lr) {
        return switch (lr) {
            case "L" -> turnLeft();
            case "R" -> turnRight();
            default -> NORTH;
        };
    }

    public Direction turnLeft() {
        return getCardPoint(WEST, NORTH, EAST, SOUTH);
    }

    public Direction turnRight() {
        return getCardPoint(EAST, SOUTH, WEST, NORTH);
    }

    private Direction getCardPoint(Direction nextNorth, Direction nextEast, Direction nextSouth, Direction nextWest) {
        return switch (this) {
            case NORTH -> nextNorth;
            case EAST -> nextEast;
            case SOUTH -> nextSouth;
            case WEST -> nextWest;
        };
    }
}
