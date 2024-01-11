package aoc.exercises.year2023.day04;

import utilities.AbstractInputParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputParser extends AbstractInputParser<Map<Integer, Card>> {

    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<Integer, Card> parseInput() {
        return inputList.stream()
                .map(this::getCard)
                .collect(Collectors.toMap(Card::getId, card -> card));
    }

    private Card getCard(String s) {
        Card card = new Card();
        String[] elements = s.split("[:|]");
        String cardId = elements[0].trim();
        String numbers = elements[1].trim();
        String winningNumbers = elements[2].trim();

        card.setId(Integer.parseInt(cardId.replaceAll("\\D", "")));
        Arrays.stream(numbers.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .forEach(card::addNumber);
        Arrays.stream(winningNumbers.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .forEach(card::addWinningNumber);
        return card;
    }
}
