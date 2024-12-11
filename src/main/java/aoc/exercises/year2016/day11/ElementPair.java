package aoc.exercises.year2016.day11;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.tuple.Pair;

@EqualsAndHashCode
@Data
public class ElementPair {
    private String name;
    private FactoryElement generator;
    private FactoryElement microchip;

    public ElementPair() {
    }

    public ElementPair(String name) {
        this.name = name;
    }

    public ElementPair(ElementPair elementPair) {
        this.name = elementPair.getName();
        this.generator = elementPair.getGenerator() != null ? new FactoryElement(elementPair.getGenerator()) : null;
        this.microchip = elementPair.getMicrochip() != null ? new FactoryElement(elementPair.getMicrochip()) : null;
    }

    public static ElementPair parse(String input) {
        ElementPair elementPair = new ElementPair();
        FactoryElement factoryElement = new FactoryElement(input);
        if (factoryElement.getType() == FactoryElementType.GENERATOR) {
            elementPair.setGenerator(factoryElement);
        } else if (factoryElement.getType() == FactoryElementType.MICROCHIP) {
            elementPair.setMicrochip(factoryElement);
        }
        elementPair.setName(factoryElement.getName());
        return elementPair;
    }

    public boolean isEmpty() {
        return generator == null && microchip == null;
    }

    public void add(FactoryElement factoryElement) {
        if (factoryElement.getType() == FactoryElementType.MICROCHIP) {
            microchip = factoryElement;
        } else if (factoryElement.getType() == FactoryElementType.GENERATOR) {
            generator = factoryElement;
        }
    }

    public void remove(FactoryElement factoryElement) {
        if (factoryElement.getType() == FactoryElementType.GENERATOR) {
            generator = null;
        } else if (factoryElement.getType() == FactoryElementType.MICROCHIP) {
            microchip = null;
        }
    }

    public void fill(ElementPair elementPair) {
        if (microchip == null) {
            microchip = elementPair.getMicrochip();
        } else if (generator == null) {
            generator = elementPair.getGenerator();
        }
    }

    public Pair<FactoryElement, FactoryElement> toPair() {
        if (microchip == null && generator == null) {
            return null;
        }
        return Pair.of(generator, microchip);
    }

    public boolean hasMatchingGenerator(FactoryElement factoryElement) {
        return generator != null && generator.getName().equals(factoryElement.getName());
    }

    public String toString() {
        String generatorPart = generator != null ? "GENERATOR" : "";
        String microchipPart = microchip != null ? "MICROCHIP" : "";

        return generatorPart + ((!generatorPart.isBlank() && !microchipPart.isBlank()) ? ", " : "") + microchipPart;
    }

    public int countAllElements() {
        return (generator != null ? 1 : 0) + (microchip != null ? 1 : 0);
    }
}
