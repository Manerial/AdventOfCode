package aoc.exercises.year2016.day11;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode
@Getter
public class FactoryElement {
    private FactoryElementType type;
    private String name;

    public FactoryElement(String element) {
        Pattern pattern = Pattern.compile("(\\w+)(-compatible)? (generator|microchip)");
        Matcher matcher = pattern.matcher(element);
        if (matcher.find()) {
            name = matcher.group(1);
            type = FactoryElementType.valueOf(matcher.group(3).toUpperCase());
        }
    }

    public FactoryElement(FactoryElementType type, String name) {
        this.type = type;
        this.name = name;
    }

    public FactoryElement(FactoryElement factoryElement) {
        this.type = factoryElement.getType();
        this.name = factoryElement.getName();
    }

    public boolean match(FactoryElement generator) {
        return generator.getName().equals(this.getName());
    }
}
