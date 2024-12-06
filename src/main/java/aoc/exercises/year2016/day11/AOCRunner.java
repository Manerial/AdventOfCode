package aoc.exercises.year2016.day11;

import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Map<RadioisotopeTestingFacility, Integer> configInMinMoves = new HashMap<>();

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        RadioisotopeTestingFacility radioisotopeTestingFacility = inputParser.parseInput();
        solution1 = moveAllUp(radioisotopeTestingFacility, 0);

        radioisotopeTestingFacility = inputParser.parseInput();
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.GENERATOR, "elerium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.MICROCHIP, "elerium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.GENERATOR, "dilithium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.MICROCHIP, "dilithium"));
        solution2 = moveAllUp(radioisotopeTestingFacility, 0);
    }

    private int moveAllUp(RadioisotopeTestingFacility radioisotopeTestingFacility, int currentFloor) {
        if (radioisotopeTestingFacility.hasAllMovedUp()) {
            return 0;
        }
        int minMoves = Integer.MAX_VALUE;
        List<Pair<FactoryElement, FactoryElement>> canMoveUp = radioisotopeTestingFacility.getElementsThatCanMoveUp(currentFloor);
        for (Pair<FactoryElement, FactoryElement> pair : canMoveUp) {
            RadioisotopeTestingFacility cloneRTF = radioisotopeTestingFacility.cloneRTF();
            cloneRTF.moveUp(pair, currentFloor);
            minMoves = Math.min(minMoves, moveAllUp(cloneRTF, currentFloor + 1) + 1);
        }
        List<Pair<FactoryElement, FactoryElement>> canMoveDown = radioisotopeTestingFacility.getElementsThatCanMoveDown(currentFloor);
        for (Pair<FactoryElement, FactoryElement> pair : canMoveDown) {
            RadioisotopeTestingFacility cloneRTF = radioisotopeTestingFacility.cloneRTF();
            cloneRTF.moveDown(pair, currentFloor);
            minMoves = Math.min(minMoves, moveAllUp(cloneRTF, currentFloor - 1) + 1);
        }
        return minMoves;
    }
}