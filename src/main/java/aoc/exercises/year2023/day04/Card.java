package aoc.exercises.year2023.day04;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Card {
    private int id;
    private int stack = 1;
    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> winningNumbers = new ArrayList<>();

    public int getPoints() {
        int points = countWinNumbers() - 1;
        return (int) Math.pow(2, points);
    }

    public int countWinNumbers() {
        return (int) numbers.stream()
                .filter(number -> winningNumbers.contains(number))
                .count();
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public void addWinningNumber(int number) {
        winningNumbers.add(number);
    }

    public void addToStack(int addedStack) {
        stack += addedStack;
    }
}
