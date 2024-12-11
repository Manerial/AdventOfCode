package aoc.exercises.year2016.day11;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@NoArgsConstructor
@EqualsAndHashCode
public class RTFFloor {
    Map<String, ElementPair> pairGeneratorMicrochipByNames = new HashMap<>();

    public RTFFloor(String line) {
        if (!line.contains("contains nothing relevant")) {
            String[] parts = line.split("contains ")[1].split("(, and|,|and)");
            for (String part : parts) {
                ElementPair elementPair = ElementPair.parse(part.trim());
                String name = elementPair.getName();
                pairGeneratorMicrochipByNames.compute(name, (k, v) -> {
                    if (v == null) {
                        return elementPair;
                    }
                    v.fill(elementPair);
                    return v;
                });
            }
        }
    }

    public RTFFloor(RTFFloor rtfFloor) {
        rtfFloor.pairGeneratorMicrochipByNames.forEach((k, v) -> this.pairGeneratorMicrochipByNames.put(k, new ElementPair(v)));
    }

    public void addElement(FactoryElement factoryElement) {
        if (factoryElement == null) {
            return;
        }
        String name = factoryElement.getName();
        pairGeneratorMicrochipByNames.compute(name, (k, v) -> {
            if (v == null) {
                v = new ElementPair(name);
            }
            v.add(factoryElement);
            return v;
        });
    }

    public void removeElement(FactoryElement factoryElement) {
        if (factoryElement == null) {
            return;
        }
        String name = factoryElement.getName();
        ElementPair elementPair = pairGeneratorMicrochipByNames.get(name);
        elementPair.remove(factoryElement);
    }

    public boolean hasNoElements() {
        return pairGeneratorMicrochipByNames.values().stream()
                .allMatch(ElementPair::isEmpty);
    }

    public List<Pair<FactoryElement, FactoryElement>> getAllPairs(RTFFloor nextFloor) {
        Pair<FactoryElement, FactoryElement> pairM = getMPair(nextFloor);
        Pair<FactoryElement, FactoryElement> pairG = getGPair(nextFloor);
        Pair<FactoryElement, FactoryElement> pairMM = getMMPair(nextFloor);
        Pair<FactoryElement, FactoryElement> pairGM = getGMPair(nextFloor);
        Pair<FactoryElement, FactoryElement> pairGG = getGGPair(nextFloor);
        List<Pair<FactoryElement, FactoryElement>> allPairs = new ArrayList<>();
        if (pairGM != null) {
            allPairs.add(pairGM);
        }
        if (pairM != null) {
            allPairs.add(pairM);
        }
        if (pairG != null) {
            allPairs.add(pairG);
        }
        if (pairMM != null) {
            allPairs.add(pairMM);
        }
        if (pairGG != null) {
            allPairs.add(pairGG);
        }

        return allPairs;
    }

    private Pair<FactoryElement, FactoryElement> getMPair(RTFFloor nextFloor) {
        List<FactoryElement> microchips = getAllMicrochipsThatCanMove(nextFloor);
        if (!microchips.isEmpty()) {
            return Pair.of(microchips.get(0), null);
        }
        return null;
    }

    private Pair<FactoryElement, FactoryElement> getGPair(RTFFloor nextFloor) {
        if (!nextFloor.microchipsAreProtected() && nextFloor.getAllMicrochips().size() != 1) {
            return null;
        }
        List<ElementPair> allGeneratorsOnFloor = getAllGenerators();

        return allGeneratorsOnFloor.stream()
                .filter(elementPair -> canMoveTo(elementPair, nextFloor))
                .map(elementPair -> Pair.of(elementPair.getGenerator(), (FactoryElement) null))
                .findAny()
                .orElse(null);
    }

    private Pair<FactoryElement, FactoryElement> getMMPair(RTFFloor nextFloor) {
        List<FactoryElement> microchips = getAllMicrochipsThatCanMove(nextFloor);
        if (microchips.size() == 2) {
            return Pair.of(microchips.get(0), microchips.get(1));
        }
        return null;
    }

    private Pair<FactoryElement, FactoryElement> getGMPair(RTFFloor nextFloor) {
        if (nextFloor.microchipsAreProtected()) {
            return pairGeneratorMicrochipByNames.values().stream()
                    .filter(elementPair -> elementPair.getGenerator() != null && elementPair.getMicrochip() != null)
                    .findAny()
                    .orElse(new ElementPair())
                    .toPair();
        }
        return null;
    }


    private Pair<FactoryElement, FactoryElement> getGGPair(RTFFloor nextFloor) {
        List<ElementPair> allGeneratorsOnFloor = getAllGenerators();
        if (allGeneratorsOnFloor.size() == 1) {
            return null;
        }

        List<Pair<ElementPair, ElementPair>> allGGpairs = new ArrayList<>();
        allGeneratorsOnFloor.forEach(factoryElement1 -> allGeneratorsOnFloor.stream()
                .filter(factoryElement2 -> !factoryElement1.getName().equals(factoryElement2.getName()))
                .forEach(factoryElement2 -> allGGpairs.add(Pair.of(factoryElement1, factoryElement2))));

        return allGGpairs.stream()
                .filter(pair -> canMoveTo(pair, nextFloor))
                .map(pair -> Pair.of(pair.getLeft().getGenerator(), pair.getRight().getGenerator()))
                .findAny()
                .orElse(null);
    }

    private boolean canMoveTo(ElementPair elementPair, RTFFloor nextFloor) {
        List<ElementPair> generatorsOnFloor = getAllGenerators();
        List<ElementPair> microchipsOnNextFloor = nextFloor.getAllMicrochips();

        if (nextFloor.microchipsAreProtected()) {
            if (elementPair.getMicrochip() == null) {
                return true;
            } else {
                return generatorsOnFloor.size() == 1;
            }
        } else {
            return microchipsOnNextFloor.size() == 1 && microchipsOnNextFloor.get(0).getMicrochip().match(elementPair.getGenerator());
        }
    }

    private boolean canMoveTo(Pair<ElementPair, ElementPair> elementPairOnFloor, RTFFloor nextFloor) {
        List<ElementPair> generatorsOnFloor = getAllGenerators();
        List<ElementPair> microchipsOnNextFloor = nextFloor.getAllMicrochips();
        ElementPair elementPairA = elementPairOnFloor.getLeft();
        ElementPair elementPairB = elementPairOnFloor.getRight();
        if (nextFloor.microchipsAreProtected()) {
            boolean aHasNoMicrochip = elementPairA.getMicrochip() == null;
            boolean bHasNoMicrochip = elementPairB.getMicrochip() == null;
            if (aHasNoMicrochip && bHasNoMicrochip) {
                return true;
            } else {
                return generatorsOnFloor.size() == 2;
            }
        } else {
            if (microchipsOnNextFloor.size() > 2) {
                return false;
            }
            FactoryElement microchipA = microchipsOnNextFloor.get(0).getMicrochip();
            boolean aIsCompatible = elementPairA.getGenerator().match(microchipA) || elementPairB.getGenerator().match(microchipA);
            boolean bIsCompatible = true;
            if (microchipsOnNextFloor.size() == 2) {
                FactoryElement microchipB = microchipsOnNextFloor.get(1).getMicrochip();
                bIsCompatible = elementPairA.getGenerator().match(microchipB) || elementPairB.getGenerator().match(microchipB);
            }

            return aIsCompatible && bIsCompatible;
        }
    }

    private List<ElementPair> getAllGenerators() {
        return pairGeneratorMicrochipByNames.values().stream()
                .filter(elementPair -> elementPair.getGenerator() != null)
                .toList();
    }

    protected List<ElementPair> getAllMicrochips() {
        return pairGeneratorMicrochipByNames.values().stream()
                .filter(elementPair -> elementPair.getMicrochip() != null)
                .toList();
    }

    private List<FactoryElement> getAllMicrochipsThatCanMove(RTFFloor nextFloor) {
        return pairGeneratorMicrochipByNames.values().stream()
                .map(ElementPair::getMicrochip)
                .filter(Objects::nonNull)
                .filter(factoryElement -> nextFloor.hasMatchingGenerator(factoryElement) || nextFloor.hasNoGenerator())
                .toList();
    }

    protected boolean microchipsAreProtected() {
        return pairGeneratorMicrochipByNames.values().stream()
                .allMatch(elementPair -> elementPair.getMicrochip() == null || elementPair.getGenerator() != null);
    }

    private boolean hasNoGenerator() {
        return pairGeneratorMicrochipByNames.values().stream()
                .allMatch(elementPair -> elementPair.getGenerator() == null);
    }

    private boolean hasMatchingGenerator(FactoryElement factoryElement) {
        return pairGeneratorMicrochipByNames.values().stream()
                .filter(elementPair -> elementPair.getName().equals(factoryElement.getName()))
                .anyMatch(elementPair -> elementPair.hasMatchingGenerator(factoryElement));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        pairGeneratorMicrochipByNames.forEach((key, value) -> {
            if (!value.isEmpty()) {
                sb.append(key).append(": ").append(value).append("; ");
            }
        });
        if (!sb.isEmpty()) {
            return sb.substring(0, sb.length() - 2);
        }
        return "";
    }

    public int countAllElements() {
        return pairGeneratorMicrochipByNames.values().stream()
                .mapToInt(ElementPair::countAllElements)
                .sum();
    }
}
