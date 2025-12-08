package aoc.exercises.year2025.day08;

import aoc.common_objects.*;
import org.apache.commons.lang3.tuple.*;
import utilities.*;

import java.util.*;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2025/day/8">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        int turns = isExample ? 10 : 1000;
        List<LightBox> boxes = new ArrayList<>();
        for (String line : inputList) {
            Position3D position = new Position3D(line, ",");
            LightBox box = new LightBox(position);
            boxes.add(box);
        }
        boxes.forEach(box -> box.addBoxes(boxes));

        Map<Double, Pair<LightBox, LightBox>> allDistances = new TreeMap<>();
        boxes.forEach(lightBox -> allDistances.putAll(lightBox.getDistanceToOthers()));
        allDistances.remove(0d);

        int index;
        for (index = 0; index < turns; index++) {
            Pair<LightBox, LightBox> nearestPair = allDistances.entrySet().stream()
                    .skip(index)
                    .findFirst()
                    .orElseThrow()
                    .getValue();
            nearestPair.getKey().connectTo(nearestPair.getValue());
        }

        solution1 = boxes.stream()
                .map(LightBox::getCircuit)
                .distinct()
                .sorted((o1, o2) -> o2.size() - o1.size())
                .limit(3)
                .mapToInt(Set::size)
                .reduce(1, (a, b) -> a * b);

        while (boxes.getFirst().getCircuit().size() != boxes.size()) {
            Pair<LightBox, LightBox> nearestPair = allDistances.entrySet().stream()
                    .skip(index)
                    .findFirst()
                    .orElseThrow()
                    .getValue();
            nearestPair.getKey().connectTo(nearestPair.getValue());
            index++;
        }

        Pair<LightBox, LightBox> lastPair = allDistances.entrySet().stream()
                .skip(index - 1)
                .findFirst()
                .orElseThrow()
                .getValue();

        solution2 = (long) lastPair.getLeft().getPosition().getX() * (long) lastPair.getRight().getPosition().getX();
    }
}