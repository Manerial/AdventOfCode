package aoc.exercises.year2022.day22;

import aoc.common_objects.Direction;
import aoc.common_objects.Position;
import aoc.common_objects.Position3D;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cube {
    private final int faceSize;
    private final Set<Position> allCubePositions;
    private final Face frontFace = new Face();
    private final Face backFace = new Face();
    private final Face topFace = new Face();
    private final Face bottomFace = new Face();
    private final Face leftFace = new Face();
    private final Face rightFace = new Face();
    private final Map<Position3D, Position> wholeCube = new HashMap<>();

    public Cube(int faceSize, Grid grid) {
        grid.resetPosition();
        Position initialPosition = new Position(grid.getPosition());
        allCubePositions = grid.getPositions();
        this.faceSize = faceSize;
        fillFaceThenNext(initialPosition, FacePosition.FRONT, FacePosition.TOP, FacePosition.FRONT);
        wholeCube.putAll(frontFace.getPositions());
        wholeCube.putAll(backFace.getPositions());
        wholeCube.putAll(topFace.getPositions());
        wholeCube.putAll(bottomFace.getPositions());
        wholeCube.putAll(leftFace.getPositions());
        wholeCube.putAll(rightFace.getPositions());
    }

    private void fillFaceThenNext(Position position, FacePosition current, FacePosition top, FacePosition front) {
        if (!allCubePositions.contains(position)) {
            return;
        }
        if (getFace(current).isAlreadyFilled()) {
            return;
        }
        fill(position, current, top, front);
        Position firstToLeft = firstToLeft(position);
        Position firstToRight = firstToRight(position);
        Position firstToBottom = firstToBottom(position);

        switch (current) {
            case FRONT -> {
                fillFaceThenNext(firstToLeft, FacePosition.LEFT, FacePosition.TOP, FacePosition.RIGHT);
                fillFaceThenNext(firstToRight, FacePosition.RIGHT, FacePosition.TOP, FacePosition.LEFT);
                fillFaceThenNext(firstToBottom, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.TOP);
            }
            case BACK -> {
                switch (top) {
                    case RIGHT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.LEFT);
                        fillFaceThenNext(firstToRight, FacePosition.TOP, FacePosition.FRONT, FacePosition.RIGHT);
                        fillFaceThenNext(firstToBottom, FacePosition.LEFT, FacePosition.RIGHT, FacePosition.BOTTOM);
                    }
                    case LEFT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.TOP, FacePosition.FRONT, FacePosition.LEFT);
                        fillFaceThenNext(firstToRight, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.RIGHT);
                        fillFaceThenNext(firstToBottom, FacePosition.RIGHT, FacePosition.LEFT, FacePosition.BOTTOM);
                    }
                    case BOTTOM -> {
                        fillFaceThenNext(firstToLeft, FacePosition.LEFT, FacePosition.BOTTOM, FacePosition.LEFT);
                        fillFaceThenNext(firstToRight, FacePosition.RIGHT, FacePosition.BOTTOM, FacePosition.RIGHT);
                        fillFaceThenNext(firstToBottom, FacePosition.TOP, FacePosition.FRONT, FacePosition.BOTTOM);
                    }
                    case TOP -> {
                        fillFaceThenNext(firstToLeft, FacePosition.RIGHT, FacePosition.TOP, FacePosition.LEFT);
                        fillFaceThenNext(firstToRight, FacePosition.LEFT, FacePosition.TOP, FacePosition.RIGHT);
                        fillFaceThenNext(firstToBottom, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.BOTTOM);
                    }
                }
            }
            case RIGHT -> {
                switch (front) {
                    case RIGHT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BACK, FacePosition.BOTTOM, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.TOP, FacePosition.FRONT, FacePosition.RIGHT);
                    }
                    case LEFT -> {
                        fillFaceThenNext(firstToRight, FacePosition.BACK, FacePosition.TOP, FacePosition.FRONT);
                        fillFaceThenNext(firstToBottom, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.LEFT);
                    }
                    case BOTTOM -> {
                        fillFaceThenNext(firstToLeft, FacePosition.TOP, FacePosition.FRONT, FacePosition.BOTTOM);
                        fillFaceThenNext(firstToRight, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.BOTTOM);
                    }
                    case TOP -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.TOP);
                        fillFaceThenNext(firstToRight, FacePosition.TOP, FacePosition.FRONT, FacePosition.TOP);
                        fillFaceThenNext(firstToBottom, FacePosition.BACK, FacePosition.RIGHT, FacePosition.BACK);
                    }
                }
            }
            case LEFT -> {
                switch (front) {
                    case RIGHT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BACK, FacePosition.TOP, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.RIGHT);
                    }
                    case LEFT -> {
                        fillFaceThenNext(firstToRight, FacePosition.BACK, FacePosition.TOP, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.TOP, FacePosition.FRONT, FacePosition.LEFT);
                    }
                    case BOTTOM -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.BOTTOM);
                        fillFaceThenNext(firstToRight, FacePosition.TOP, FacePosition.FRONT, FacePosition.BOTTOM);
                    }
                    case TOP -> {
                        fillFaceThenNext(firstToLeft, FacePosition.TOP, FacePosition.FRONT, FacePosition.TOP);
                        fillFaceThenNext(firstToRight, FacePosition.BOTTOM, FacePosition.BACK, FacePosition.TOP);
                        fillFaceThenNext(firstToBottom, FacePosition.BACK, FacePosition.LEFT, FacePosition.BACK);
                    }
                }
            }
            case BOTTOM -> {
                switch (front) {
                    case RIGHT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BACK, FacePosition.LEFT, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.RIGHT, FacePosition.BOTTOM, FacePosition.RIGHT);
                    }
                    case LEFT -> {
                        fillFaceThenNext(firstToRight, FacePosition.BACK, FacePosition.RIGHT, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.LEFT, FacePosition.BOTTOM, FacePosition.LEFT);
                    }
                    case BOTTOM -> {
                        fillFaceThenNext(firstToLeft, FacePosition.RIGHT, FacePosition.LEFT, FacePosition.BOTTOM);
                        fillFaceThenNext(firstToRight, FacePosition.LEFT, FacePosition.RIGHT, front.rotate(Direction.WEST));
                    }
                    case TOP -> {
                        fillFaceThenNext(firstToLeft, FacePosition.LEFT, FacePosition.LEFT, FacePosition.TOP);
                        fillFaceThenNext(firstToRight, FacePosition.RIGHT, FacePosition.RIGHT, FacePosition.TOP);
                        fillFaceThenNext(firstToBottom, FacePosition.BACK, FacePosition.BOTTOM, FacePosition.BACK);
                    }
                }
            }
            case TOP -> {
                switch (front) {
                    case RIGHT -> {
                        fillFaceThenNext(firstToLeft, FacePosition.BACK, FacePosition.RIGHT, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.LEFT, FacePosition.TOP, FacePosition.RIGHT);
                    }
                    case LEFT -> {
                        fillFaceThenNext(firstToRight, FacePosition.BACK, FacePosition.LEFT, FacePosition.BACK);
                        fillFaceThenNext(firstToBottom, FacePosition.RIGHT, FacePosition.TOP, FacePosition.LEFT);
                    }
                    case BOTTOM -> {
                        fillFaceThenNext(firstToLeft, FacePosition.LEFT, FacePosition.RIGHT, FacePosition.BOTTOM);
                        fillFaceThenNext(firstToRight, FacePosition.RIGHT, FacePosition.LEFT, FacePosition.BOTTOM);
                    }
                    case TOP -> {
                        fillFaceThenNext(firstToLeft, FacePosition.RIGHT, FacePosition.RIGHT, FacePosition.TOP);
                        fillFaceThenNext(firstToRight, FacePosition.LEFT, FacePosition.LEFT, FacePosition.TOP);
                        fillFaceThenNext(firstToBottom, FacePosition.BACK, FacePosition.TOP, FacePosition.BACK);
                    }
                }
            }
        }
    }

    private Position firstToBottom(Position initialPosition) {
        return new Position(initialPosition.getX(), initialPosition.getY() + faceSize);
    }

    private Position firstToRight(Position initialPosition) {
        return new Position(initialPosition.getX() + faceSize, initialPosition.getY());
    }

    private Position firstToLeft(Position initialPosition) {
        return new Position(initialPosition.getX() - faceSize, initialPosition.getY());
    }

    private void fill(Position initialPosition, FacePosition facePosition, FacePosition top, FacePosition front) {
        Face face = getFace(facePosition);
        Map<Position3D, Position> facePositions = new HashMap<>();
        int startX = initialPosition.getX();
        int startY = initialPosition.getY();
        for (int x = startX; x < faceSize + startX; x++) {
            for (int y = startY; y < faceSize + startY; y++) {
                Position3D position3D = getPosition3D(facePosition, (x - 1) % faceSize, (y - 1) % faceSize, top, front);
                facePositions.put(position3D, new Position(x, y));
            }
        }
        face.setPositions(facePositions);
    }

    private Face getFace(FacePosition face) {
        Face faceToReturn;
        switch (face) {
            case FRONT -> faceToReturn = frontFace;
            case BACK -> faceToReturn = backFace;
            case LEFT -> faceToReturn = leftFace;
            case RIGHT -> faceToReturn = rightFace;
            case TOP -> faceToReturn = topFace;
            case BOTTOM -> faceToReturn = bottomFace;
            default -> throw new IllegalArgumentException();
        }
        return faceToReturn;
    }

    private Position3D getPosition3D(FacePosition facePosition, int a, int b, FacePosition whereIsTheTop, FacePosition whereIsTheFrontFace) {
        int minBound = -1;
        int maxBound = faceSize;
        Position3D position3D = null;
        switch (whereIsTheFrontFace) {
            case FRONT -> position3D = new Position3D(a, b, minBound);
            case BACK -> {
                // On sait qu'on est sur la face back, on doit savoir où se trouve top
                switch (whereIsTheTop) {
                    case LEFT -> position3D = new Position3D(b, a, maxBound);
                    case RIGHT -> position3D = new Position3D(faceSize - 1 - b, faceSize - 1 - a, maxBound);
                    case TOP -> position3D = new Position3D(faceSize - 1 - a, b, maxBound);
                    case BOTTOM -> position3D = new Position3D(a, faceSize - 1 - b, maxBound);
                    default -> {
                        // Nothing to do
                    }
                }
            }
            case LEFT -> {
                switch (facePosition) {
                    case LEFT -> position3D = new Position3D(minBound, faceSize - 1 - b, a);
                    case RIGHT -> position3D = new Position3D(maxBound, b, a);
                    case TOP -> position3D = new Position3D(b, minBound, a);
                    case BOTTOM -> position3D = new Position3D(faceSize - 1 - b, maxBound, a);
                    default -> {
                        // Nothing to do
                    }
                }
            }
            case RIGHT -> {
                switch (facePosition) {
                    case LEFT -> position3D = new Position3D(minBound, b, faceSize - 1 - a);
                    case RIGHT -> position3D = new Position3D(maxBound, faceSize - 1 - b, faceSize - 1 - a);
                    case TOP -> position3D = new Position3D(faceSize - 1 - b, minBound, faceSize - 1 - a);
                    case BOTTOM -> position3D = new Position3D(b, maxBound, faceSize - 1 - a);
                    default -> {
                        // Nothing to do
                    }
                }
            }
            case TOP -> {
                switch (facePosition) {
                    case LEFT -> position3D = new Position3D(minBound, a, b);
                    case RIGHT -> position3D = new Position3D(maxBound, faceSize - 1 - a, b);
                    case TOP -> position3D = new Position3D(faceSize - 1 - a, minBound, b);
                    case BOTTOM -> position3D = new Position3D(a, maxBound, b);
                    default -> {
                        // Nothing to do
                    }
                }
            }
            case BOTTOM -> {
                switch (facePosition) {
                    case LEFT -> position3D = new Position3D(minBound, faceSize - 1 - a, faceSize - 1 - b);
                    case RIGHT -> position3D = new Position3D(maxBound, a, faceSize - 1 - b);
                    case TOP -> position3D = new Position3D(a, minBound, faceSize - 1 - b);
                    case BOTTOM -> position3D = new Position3D(faceSize - 1 - a, maxBound, faceSize - 1 - b);
                    default -> {
                        // Nothing to do
                    }
                }
            }
        }
        return position3D;
    }

    public Position warp(Position position, Position previousPosition) {
        Position3D previousPosition3D = wholeCube.entrySet().stream()
                .filter(entry -> entry.getValue().equals(previousPosition))
                .findAny()
                .orElseThrow()
                .getKey();
        Position3D position3D = wholeCube.entrySet().stream()
                .filter(entry -> entry.getValue().equals(position))
                .findAny()
                .orElseThrow()
                .getKey();
        Position3D nextPosition3D = new Position3D(position3D.getX(), position3D.getY(), position3D.getZ());

        int movedOnX = position3D.getX() - previousPosition3D.getX();
        int movedOnY = position3D.getY() - previousPosition3D.getY();
        int movedOnZ = position3D.getZ() - previousPosition3D.getZ();

        if (movedOnX != 0) {
            if (movedOnX == 1) {
                nextPosition3D.setX(faceSize);
            } else {
                nextPosition3D.setX(-1);
            }
            if (position3D.getY() == -1) {
                nextPosition3D.setY(0);
            } else if (position3D.getY() == faceSize) {
                nextPosition3D.setY(faceSize - 1);
            } else if (position3D.getZ() == -1) {
                nextPosition3D.setZ(0);
            } else if (position3D.getZ() == faceSize) {
                nextPosition3D.setZ(faceSize - 1);
            }
        } else if (movedOnY != 0) {
            if (movedOnY == 1) {
                nextPosition3D.setY(faceSize);
            } else {
                nextPosition3D.setY(-1);
            }
            if (position3D.getX() == -1) {
                nextPosition3D.setX(0);
            } else if (position3D.getX() == faceSize) {
                nextPosition3D.setX(faceSize - 1);
            } else if (position3D.getZ() == -1) {
                nextPosition3D.setZ(0);
            } else if (position3D.getZ() == faceSize) {
                nextPosition3D.setZ(faceSize - 1);
            }
        } else if (movedOnZ != 0) {
            if (movedOnZ == 1) {
                nextPosition3D.setZ(faceSize);
            } else {
                nextPosition3D.setZ(-1);
            }
            if (position3D.getX() == -1) {
                nextPosition3D.setX(0);
            } else if (position3D.getX() == faceSize) {
                nextPosition3D.setX(faceSize - 1);
            } else if (position3D.getY() == -1) {
                nextPosition3D.setY(0);
            } else if (position3D.getY() == faceSize) {
                nextPosition3D.setY(faceSize - 1);
            }
        }

        return wholeCube.get(nextPosition3D);
    }

    public Direction turn(Position position, Position nextPosition, Direction direction) {

        boolean nextOnTop = (nextPosition.getY() - 1) % faceSize == 0;
        boolean nextOnBottom = (nextPosition.getY() - 1) % faceSize == faceSize - 1;
        boolean nextOnLeft = (nextPosition.getX() - 1) % faceSize == 0;
        boolean nextOnRight = (nextPosition.getX() - 1) % faceSize == faceSize - 1;

        if (nextOnTop && (!nextOnRight && !nextOnLeft)) {
            return Direction.SOUTH;
        } else if (nextOnBottom && (!nextOnRight && !nextOnLeft)) {
            return Direction.NORTH;
        } else if (nextOnRight && (!nextOnBottom && !nextOnTop)) {
            return Direction.WEST;
        } else if (nextOnLeft && (!nextOnBottom && !nextOnTop)) {
            return Direction.EAST;
        }

        boolean nextOnTopLeft = nextOnTop && nextOnLeft;
        boolean nextOnTopRight = nextOnTop && nextOnRight;
        boolean nextOnBottomLeft = nextOnBottom && nextOnLeft;
        boolean nextOnBottomRight = nextOnBottom && nextOnRight;

        int absoluteX = position.getX() % faceSize;
        int absoluteY = position.getY() % faceSize;

        boolean onTop = absoluteY == 0;
        boolean onLeft = absoluteX == 0;

        switch (direction) {
            case SOUTH -> {
                if (onLeft) { // Bottom left
                    if (nextOnTopLeft) {
                        return Direction.SOUTH;
                    } else if (nextOnTopRight) {
                        return Direction.WEST;
                    } else if (nextOnBottomLeft) {
                        return Direction.EAST;
                    } else if (nextOnBottomRight) {
                        return Direction.NORTH;
                    }
                } else { // Bottom right
                    if (nextOnTopLeft) {
                        return Direction.EAST;
                    } else if (nextOnTopRight) {
                        return Direction.SOUTH;
                    } else if (nextOnBottomLeft) {
                        return Direction.NORTH;
                    } else if (nextOnBottomRight) {
                        return Direction.WEST;
                    }
                }
            }
            case NORTH -> {
                if (onLeft) { // Top left
                    if (nextOnTopLeft) {
                        return Direction.EAST;
                    } else if (nextOnTopRight) {
                        return Direction.SOUTH;
                    } else if (nextOnBottomLeft) {
                        return Direction.NORTH;
                    } else if (nextOnBottomRight) {
                        return Direction.WEST;
                    }
                } else { // Top right
                    if (nextOnTopLeft) {
                        return Direction.SOUTH;
                    } else if (nextOnTopRight) {
                        return Direction.WEST;
                    } else if (nextOnBottomLeft) {
                        return Direction.EAST;
                    } else if (nextOnBottomRight) {
                        return Direction.NORTH;
                    }
                }
            }
            case WEST -> {
                if (onTop) { // Top left
                    if (nextOnTopLeft) {
                        return Direction.SOUTH;
                    } else if (nextOnTopRight) {
                        return Direction.WEST;
                    } else if (nextOnBottomLeft) {
                        return Direction.EAST;
                    } else if (nextOnBottomRight) {
                        return Direction.NORTH;
                    }
                } else { // Bottom Left
                    if (nextOnTopLeft) {
                        return Direction.EAST;
                    } else if (nextOnTopRight) {
                        return Direction.SOUTH;
                    } else if (nextOnBottomLeft) {
                        return Direction.NORTH;
                    } else if (nextOnBottomRight) {
                        return Direction.WEST;
                    }
                }
            }
            case EAST -> {
                if (onTop) { // Top right
                    if (nextOnTopLeft) {
                        return Direction.EAST;
                    } else if (nextOnTopRight) {
                        return Direction.SOUTH;
                    } else if (nextOnBottomLeft) {
                        return Direction.NORTH;
                    } else if (nextOnBottomRight) {
                        return Direction.WEST;
                    }
                } else { // Bottom right
                    if (nextOnTopLeft) {
                        return Direction.SOUTH;
                    } else if (nextOnTopRight) {
                        return Direction.WEST;
                    } else if (nextOnBottomLeft) {
                        return Direction.EAST;
                    } else if (nextOnBottomRight) {
                        return Direction.NORTH;
                    }
                }
            }
        }

        throw new IllegalArgumentException("Cannot turn " + position + " to " + nextPosition);
    }
}
