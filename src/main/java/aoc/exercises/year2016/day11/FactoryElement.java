package aoc.exercises.year2016.day11;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
