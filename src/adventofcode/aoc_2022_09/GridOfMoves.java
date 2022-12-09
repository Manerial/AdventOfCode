package adventofcode.aoc_2022_09;

import utilities.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class GridOfMoves {
    private int xHead = 0;
    private int yHead = 0;
    private int xQueue = 0;
    private int yQueue = 0;
    private final Map<Integer, List<Integer>> gridQueue = new TreeMap<>();

    public boolean move(String direction, int move) {
        int copyX = xHead;
        int copyY = yHead;
        switch (direction) {
            case "L":
                while (xHead > copyX - move) {
                    xHead--;
                    play();
                }
                break;
            case "R":
                while (xHead < copyX + move) {
                    xHead++;
                    play();
                }
                break;
            case "U":
                while (yHead > copyY - move) {
                    yHead--;
                    play();
                }
                break;
            case "D":
                while (yHead < copyY + move) {
                    yHead++;
                    play();
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void play() {
        getQueuePos();

        Printer.println("Head : " + xHead + " " + yHead);
        Printer.println("Queue : " + xQueue + " " + yQueue);
        Printer.println();

        gridQueue.putIfAbsent(xQueue, new ArrayList<>());
        if (!gridQueue.get(xQueue).contains(yQueue)) {
            gridQueue.get(xQueue).add(yQueue);
        }
    }

    private void getQueuePos() {
        int deltaX = xHead - xQueue;
        int deltaY = yHead - yQueue;
        switch (deltaX) {
            case 2:
                xQueue++;
                checkDiagY(deltaY);
                break;
            case -2:
                xQueue--;
                checkDiagY(deltaY);
                break;
            default:
                break;
        }
        switch (deltaY) {
            case 2:
                yQueue++;
                checkDiagX(deltaX);
                break;
            case -2:
                yQueue--;
                checkDiagX(deltaX);
                break;
            default:
                break;
        }
    }

    private void checkDiagX(int deltaX) {
        if (deltaX == 1) {
            xQueue++;
        } else if (deltaX == -1) {
            xQueue--;
        }
    }

    private void checkDiagY(int deltaY) {
        if (deltaY == 1) {
            yQueue++;
        } else if (deltaY == -1) {
            yQueue--;
        }
    }

    public int countQueue() {
        return gridQueue.values().stream().map(List::size).reduce(Integer::sum).get();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        gridQueue.values().forEach(Collections::sort);
        gridQueue.forEach((posX, mapY) -> stringBuilder.append(posX).append(" > ").append(mapY).append("\r\n"));
        return stringBuilder.toString();
    }
}
