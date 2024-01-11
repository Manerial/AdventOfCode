package aoc.exercises.year2023.day04;

import utilities.AbstractAOC;

import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2023/day/4">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Map<Integer, Card> cardsById = inputParser.parseInput();
        cardsById.values().forEach(card -> scratchCard(card, cardsById));

        solution1 = cardsById.values().stream()
                .mapToInt(Card::getPoints)
                .sum();
        solution2 = cardsById.values().stream()
                .mapToInt(Card::getStack)
                .sum();
    }

    private static void scratchCard(Card card, Map<Integer, Card> cardsById) {
        int winPoints = card.countWinNumbers();
        for (int i = 1; i <= winPoints; i++) {
            int nextCardId = card.getId() + i;
            cardsById.computeIfPresent(nextCardId, (id, nextCard) -> {
                nextCard.addToStack(card.getStack());
                return nextCard;
            });
        }
    }
}