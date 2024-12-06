package aoc.exercises.year2016.day11;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class RTFFloor {
    List<FactoryElement> elements = new ArrayList<>();

    public RTFFloor(String line) {
        if (!line.contains("contains nothing relevant")) {
            String[] parts = line.split("contains ")[1].split("(,|and)");
            for (String part : parts) {
                elements.add(new FactoryElement(part.trim()));
            }
        }
    }

    public RTFFloor(RTFFloor value) {
        this.elements = new ArrayList<>(value.elements);
    }

    public void addElement(FactoryElement factoryElement) {
        elements.add(factoryElement);
    }

    public void removeElement(FactoryElement factoryElement) {
        elements.remove(factoryElement);
    }

    public boolean hasElements() {
        return !elements.isEmpty();
    }

    public List<Pair<FactoryElement, FactoryElement>> getAllPairs(RTFFloor nextFloor) {

    }
}
