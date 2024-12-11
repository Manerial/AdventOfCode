package aoc.exercises.year2016.day11;

import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;
import utilities.Timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Map<String, Integer> configInMinMoves = new HashMap<>();
    private int maxMoves;

    @Override
    public void run() {
        Timer timer = new Timer();
        InputParser inputParser = new InputParser(inputList);
        RadioisotopeTestingFacility radioisotopeTestingFacility = inputParser.parseInput();
        maxMoves = getMaxMoves(radioisotopeTestingFacility);
        solution1 = moveAllUp(radioisotopeTestingFacility, 0, 0);
        System.out.println(solution1);
        timer.time();

        radioisotopeTestingFacility = inputParser.parseInput();
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.GENERATOR, "elerium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.MICROCHIP, "elerium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.GENERATOR, "dilithium"));
        radioisotopeTestingFacility.addAtFloor(0, new FactoryElement(FactoryElementType.MICROCHIP, "dilithium"));
        maxMoves = getMaxMoves(radioisotopeTestingFacility);
        solution2 = moveAllUp(radioisotopeTestingFacility, 0, 0);
        System.out.println(solution2);
        timer.time();
    }

    private static int getMaxMoves(RadioisotopeTestingFacility radioisotopeTestingFacility) {
        return radioisotopeTestingFacility.countAllElements() * radioisotopeTestingFacility.getFloors().size();
    }

    private int moveAllUp(RadioisotopeTestingFacility currentRtf, int currentFloor, int currentCount) {
        if (currentCount > maxMoves) {
            return Integer.MAX_VALUE;
        }
        if (!optimalConfig(currentRtf, currentCount, currentFloor)) {
            return Integer.MAX_VALUE;
        }
        if (currentRtf.hasAllMovedUp()) {
            return currentCount;
        }
        int minMoves = Integer.MAX_VALUE;
        List<Pair<FactoryElement, FactoryElement>> canMoveUp = currentRtf.getElementsThatCanMoveUp(currentFloor);
        for (Pair<FactoryElement, FactoryElement> pair : canMoveUp) {
            RadioisotopeTestingFacility cloneRTF = new RadioisotopeTestingFacility(currentRtf);
            cloneRTF.moveUp(pair, currentFloor);
            minMoves = Math.min(minMoves, moveAllUp(cloneRTF, currentFloor + 1, currentCount + 1));
        }
        List<Pair<FactoryElement, FactoryElement>> canMoveDown = currentRtf.getElementsThatCanMoveDown(currentFloor);
        for (Pair<FactoryElement, FactoryElement> pair : canMoveDown) {
            RadioisotopeTestingFacility cloneRTF = new RadioisotopeTestingFacility(currentRtf);
            cloneRTF.moveDown(pair, currentFloor);
            minMoves = Math.min(minMoves, moveAllUp(cloneRTF, currentFloor - 1, currentCount + 1));
        }
        return minMoves;
    }

    private boolean optimalConfig(RadioisotopeTestingFacility radioisotopeTestingFacility, int currentCount, int currentFloor) {
        String currentConfiguration = radioisotopeTestingFacility.toString() + currentFloor;
        if (currentCount > configInMinMoves.getOrDefault(currentConfiguration, Integer.MAX_VALUE)) {
            return false;
        }
        configInMinMoves.put(currentConfiguration, currentCount);
        return true;
    }
}