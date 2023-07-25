package aoc.exercises.year2022.day09;

import aoc.common_objects.Position;
import utilities.errors.NotAcceptedValue;

import java.util.*;

public class GridOfMoves {
    final List<Position> knotPositions = new ArrayList<>();
    private final Map<Integer, List<Integer>> gridQueue = new TreeMap<>();

    public GridOfMoves(int ropeSize) {
        for (int i = 0; i < ropeSize; i++) {
            knotPositions.add(new Position(0, 0));
        }
    }

    public void move(String direction, int move) {
        int copyX = knotPositions.get(0).getX();
        int copyY = knotPositions.get(0).getY();
        switch (direction) {
            case "L" -> {
                while (knotPositions.get(0).getX() > copyX - move) {
                    knotPositions.get(0).decX();
                    play();
                }
            }
            case "R" -> {
                while (knotPositions.get(0).getX() < copyX + move) {
                    knotPositions.get(0).incX();
                    play();
                }
            }
            case "U" -> {
                while (knotPositions.get(0).getY() > copyY - move) {
                    knotPositions.get(0).decY();
                    play();
                }
            }
            case "D" -> {
                while (knotPositions.get(0).getY() < copyY + move) {
                    knotPositions.get(0).incY();
                    play();
                }
            }
            default -> throw new NotAcceptedValue(direction);
        }
    }

    private void play() {
        for (int i = 1; i < knotPositions.size(); i++) {
            getQueuePos(knotPositions.get(i - 1), knotPositions.get(i));
        }

        Position queuePos = knotPositions.get(knotPositions.size() - 1);

        gridQueue.putIfAbsent(queuePos.getX(), new ArrayList<>());
        if (!gridQueue.get(queuePos.getX()).contains(queuePos.getY())) {
            gridQueue.get(queuePos.getX()).add(queuePos.getY());
        }
    }

    private void getQueuePos(Position head, Position knot) {
        int deltaX = head.getX() - knot.getX();
        int deltaY = head.getY() - knot.getY();
        switch (deltaX) {
            case 2 -> {
                knot.incX();
                checkDiagY(deltaY, knot);
            }
            case -2 -> {
                knot.decX();
                checkDiagY(deltaY, knot);
            }
            default -> {
                // Nothing
            }
        }
        switch (deltaY) {
            case 2 -> {
                knot.incY();
                checkDiagX(deltaX, knot);
            }
            case -2 -> {
                knot.decY();
                checkDiagX(deltaX, knot);
            }
            default -> {
                // Nothing
            }
        }
    }

    private void checkDiagX(int deltaX, Position knot) {
        if (deltaX == 1) {
            knot.incX();
        } else if (deltaX == -1) {
            knot.decX();
        }
    }

    private void checkDiagY(int deltaY, Position knot) {
        if (deltaY == 1) {
            knot.incY();
        } else if (deltaY == -1) {
            knot.decY();
        }
    }

    public int countQueue() {
        return gridQueue.values().stream().map(List::size).reduce(Integer::sum).orElse(0);
    }
}
