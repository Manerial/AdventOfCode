package aoc.exercises.year2023.day07;

import utilities.AbstractAOC;

import java.util.Comparator;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        List<Hand> hands = inputParser.parseInput();
        Hand.normalRules();
        solution1 = getHandsValue(hands);
        Hand.newRules();
        solution2 = getHandsValue(hands);
    }

    private int getHandsValue(List<Hand> hands) {
        hands = hands.stream()
                .sorted(Comparator
                        .comparingLong(Hand::getHandValue)
                        .thenComparing(Hand::compareCards)
                )
                .toList();
        int value = 0;
        int rank = 1;
        for (Hand hand : hands) {
            value += hand.getBid() * rank;
            rank++;
        }
        return value;
    }
}