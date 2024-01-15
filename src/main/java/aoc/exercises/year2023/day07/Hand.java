package aoc.exercises.year2023.day07;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Hand {
    public static final List<Character> CARD_LIST1 = List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    public static final List<Character> CARD_LIST2 = List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');
    public static List<Character> cardList;

    private String cards;
    private int bid;

    public static void normalRules() {
        cardList = CARD_LIST1;
    }

    public static void newRules() {
        cardList = CARD_LIST2;
    }

    public int getHandValue() {
        Map<String, Integer> cardsInHand = getCardsInHand();
        int value = 0;
        for (int occurrence : cardsInHand.values()) {
            if (occurrence == 5) {
                value += 10000;
            } else if (occurrence == 4) {
                value += 1000;
            } else if (occurrence == 3) {
                value += 100;
            } else if (occurrence == 2) {
                value += 10;
            } else {
                value += 1;
            }
        }
        return value;
    }

    public int compareCards(Hand otherHand) {
        int value = 100000;
        for (int i = 0; i < cards.length(); i++) {
            int thisValue = cardList.indexOf(cards.charAt(i));
            int thatValue = cardList.indexOf(otherHand.cards.charAt(i));
            if (thisValue != thatValue) {
                return Integer.compare(thisValue, thatValue) * value;
            }
            value /= 10;
        }
        return 0;
    }

    private Map<String, Integer> getCardsInHand() {
        Map<String, Integer> cardsInHand = new HashMap<>();
        for (String card : cards.split("")) {
            cardsInHand.put(card, cardsInHand.getOrDefault(card, 0) + 1);
        }

        if (cardList == CARD_LIST2) {
            applyNewRules(cardsInHand);
        }

        return cardsInHand;
    }

    private void applyNewRules(Map<String, Integer> cardsInHand) {
        if (!cards.equals("JJJJJ") && cardsInHand.containsKey("J")) {
            Map.Entry<String, Integer> maxEntry = cardsInHand.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("J"))
                    .max(Map.Entry.comparingByValue())
                    .orElseThrow();
            maxEntry.setValue(maxEntry.getValue() + cardsInHand.get("J"));
            cardsInHand.remove("J");
        }
    }
}
