package aoc.exercises.year2023.day07;

import utilities.AbstractInputParser;

import java.util.ArrayList;
import java.util.List;

public class InputParser extends AbstractInputParser<List<Hand>> {
    protected InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Hand> parseInput() {
        List<Hand> hands = new ArrayList<>();
        for (String line : inputList) {
            String[] split = line.split(" ");
            Hand hand = new Hand(split[0], Integer.parseInt(split[1]));
            hands.add(hand);
        }
        return hands;
    }
}
