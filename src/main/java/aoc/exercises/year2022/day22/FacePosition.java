package aoc.exercises.year2022.day22;

import aoc.common_objects.Direction;

public enum FacePosition {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM;

    public FacePosition rotate(Direction direction) {
        FacePosition result = this;
        switch (direction) {
            case SOUTH -> {
                switch (this) {
                    case FRONT -> result = BOTTOM;
                    case BACK -> result = TOP;
                    case TOP -> result = FRONT;
                    case BOTTOM -> result = BACK;
                }
            }
            case NORTH -> {
                switch (this) {
                    case FRONT -> result = TOP;
                    case BACK -> result = BOTTOM;
                    case TOP -> result = BACK;
                    case BOTTOM -> result = FRONT;
                }
            }
            case EAST -> {
                switch (this) {
                    case FRONT -> result = RIGHT;
                    case BACK -> result = LEFT;
                    case RIGHT -> result = BACK;
                    case LEFT -> result = FRONT;
                }
            }
            case WEST -> {
                switch (this) {
                    case FRONT -> result = LEFT;
                    case BACK -> result = RIGHT;
                    case RIGHT -> result = FRONT;
                    case LEFT -> result = BACK;
                }
            }
        }
        return result;
    }
}
