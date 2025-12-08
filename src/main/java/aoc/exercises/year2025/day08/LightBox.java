package aoc.exercises.year2025.day08;

import aoc.common_objects.*;
import lombok.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class LightBox {
    private final Position3D position;
    private final Map<Double, Pair<LightBox, LightBox>> distanceToOthers = new TreeMap<>();
    private Set<LightBox> circuit = new HashSet<>();

    public void addBoxes(List<LightBox> boxes) {
        for (LightBox box : boxes) {
            addBox(box);
            if (this == box) {
                circuit.add(this);
            }
        }
    }

    public void addBox(LightBox box) {
        distanceToOthers.put(position.euclidianDistanceTo(box.position), Pair.of(this, box));
    }

    public void connectTo(LightBox value) {
        Set<LightBox> newCircuit = new HashSet<>();
        newCircuit.addAll(circuit);
        newCircuit.addAll(value.circuit);

        newCircuit.forEach(box -> box.circuit = newCircuit);
    }

    @Override
    public String toString() {
        return position.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LightBox box = (LightBox) o;
        return Objects.equals(position, box.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}