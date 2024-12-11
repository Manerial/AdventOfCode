package aoc.exercises.year2016.day11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class RadioisotopeTestingFacility {
    private Map<Integer, RTFFloor> floors = new HashMap<>();

    public RadioisotopeTestingFacility(RadioisotopeTestingFacility rtf) {
        for (Map.Entry<Integer, RTFFloor> entry : rtf.floors.entrySet()) {
            this.floors.put(entry.getKey(), new RTFFloor(entry.getValue()));
        }
    }

    public void addFloor(RTFFloor rttFloor) {
        floors.put(floors.size(), rttFloor);
    }

    public void addAtFloor(int i, FactoryElement factoryElement) {
        floors.get(i).addElement(factoryElement);
    }

    public boolean hasAllMovedUp() {
        return floors.entrySet().stream()
                .filter(entry -> entry.getKey() != floors.size() - 1)
                .allMatch(entry -> entry.getValue().hasNoElements());
    }

    public List<Pair<FactoryElement, FactoryElement>> getElementsThatCanMoveDown(int currentFloor) {
        if (currentFloor > 0) {
            RTFFloor downFloor = floors.get(currentFloor - 1);
            return floors.get(currentFloor).getAllPairs(downFloor);
        }
        return List.of();
    }

    public List<Pair<FactoryElement, FactoryElement>> getElementsThatCanMoveUp(int currentFloor) {
        if (currentFloor < floors.size() - 1) {
            RTFFloor upFloor = floors.get(currentFloor + 1);
            return floors.get(currentFloor).getAllPairs(upFloor);
        }
        return List.of();
    }

    public int moveUp(Pair<FactoryElement, FactoryElement> pairOfElements, int currentFloor) {
        floors.get(currentFloor).removeElement(pairOfElements.getLeft());
        floors.get(currentFloor).removeElement(pairOfElements.getRight());
        currentFloor++;
        floors.get(currentFloor).addElement(pairOfElements.getLeft());
        floors.get(currentFloor).addElement(pairOfElements.getRight());
        return currentFloor;
    }

    public int moveDown(Pair<FactoryElement, FactoryElement> pairOfElements, int currentFloor) {
        floors.get(currentFloor).removeElement(pairOfElements.getLeft());
        floors.get(currentFloor).removeElement(pairOfElements.getRight());
        currentFloor--;
        floors.get(currentFloor).addElement(pairOfElements.getLeft());
        floors.get(currentFloor).addElement(pairOfElements.getRight());
        return currentFloor;
    }

    public int countAllElements() {
        return floors.values().stream()
                .mapToInt(RTFFloor::countAllElements)
                .sum();
    }
}
