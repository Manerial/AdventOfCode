package aoc.exercises.year2016.day03;

import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/3">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final String SPLITTER = "\\s+";
    private int nbItemsByLine;

    @Override
    public void run() {
        List<Integer> horizontalList = toHorizontalIntList();
        nbItemsByLine = getItemsByLine(inputList.get(0));

        List<PossibleShape> shapes = parseShapes(horizontalList);
        solution1 = countValidShapes(shapes);

        List<Integer> verticalList = toVerticalIntList();
        List<PossibleShape> shapesVertical = parseShapes(verticalList);
        solution2 = countValidShapes(shapesVertical);
    }

    private List<Integer> toHorizontalIntList() {
        List<Integer> horizontalList = new ArrayList<>();
        inputList.forEach(line -> Arrays.stream(line.trim().split(SPLITTER))
                        .map(Integer::parseInt)
                        .forEach(horizontalList::add)
        );
        return horizontalList;
    }

    private List<Integer> toVerticalIntList() {
        int listLength = inputList.size();
        List<Integer> verticalList = createPopulatedList(listLength * nbItemsByLine);
        for(int line = 0; line < listLength; line++) {
            String[] split = inputList.get(line).trim().split(SPLITTER);
            for(int col = 0; col < nbItemsByLine; col++) {
                verticalList.set(line + listLength * col, Integer.parseInt(split[col]));
            }
        }
        return verticalList;
    }

    private List<Integer> createPopulatedList(int listLength) {
        List<Integer> verticalList = new ArrayList<>();
        for(int i = 0; i < listLength; i++) {
            verticalList.add(0);
        }
        return verticalList;
    }

    private int getItemsByLine(String line) {
        return line.trim().split(SPLITTER).length;
    }

    private List<PossibleShape> parseShapes(List<Integer> list) {
        List<PossibleShape> shapes = new ArrayList<>();
        for (int i = 0; i < list.size(); i += nbItemsByLine) {
            shapes.add(new PossibleShape(list.subList(i, i + nbItemsByLine)));
        }
        return shapes;
    }

    private long countValidShapes(List<PossibleShape> shapes) {
        return shapes.stream()
                .filter(PossibleShape::isValid)
                .count();
    }
}