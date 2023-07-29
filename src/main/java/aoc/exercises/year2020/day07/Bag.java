package aoc.exercises.year2020.day07;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Bag {
    @Getter
    private final String name;
    private final Map<Bag, Integer> contains = new HashMap<>();

    public boolean canContain(Bag bagToCheck) {
        for(Bag bag : contains.keySet()) {
            if(bag == bagToCheck || bag.canContain(bagToCheck)) {
                return true;
            }
        }
        return false;
    }

    public void addContent(Bag contained, int number) {
        this.contains.put(contained, number);
    }

    public int countAllBags() {
        return contains.entrySet().stream().mapToInt(bagIntegerEntry -> {
            int count = bagIntegerEntry.getValue();
            count += count * bagIntegerEntry.getKey().countAllBags();
            return count;
        }).sum();
    }
}
