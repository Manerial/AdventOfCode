package aoc.exercises.year2022.day14;

import utilities.AbstractAOC;
import aoc.common_objects.Position;

import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/14">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        Cavern cavern = new Cavern(getDepth(inputList));
        cavern.setDropPosition(new Position(500, 0));
        cavern.fillWithAir();

        addRocks(cavern, inputList);

        cavern.dropSandUntilAbyss();
        solution1 = cavern.countSand();

        cavern.fillBottomWithRocks();
        cavern.dropSandUntilFull();
        solution2 = cavern.countSand();
    }

    private int getDepth(List<String> list) {
        return list.stream().flatMap(
                item -> Stream.of(item.split(" -> "))
                        .map(coordinates -> coordinates.split(",")[1])
                        .map(Integer::parseInt)
        ).max(Integer::compareTo).orElse(0);
    }

    private void addRocks(Cavern cavern, List<String> list) {
        for (String walls : list) {
            String[] wallCoordinates = walls.split(" -> ");
            for (int index = 0; index < wallCoordinates.length - 1; index++) {
                Position beginWall = new Position(wallCoordinates[index], ",");
                Position endWall = new Position(wallCoordinates[index + 1], ",");
                cavern.addRocks(beginWall, endWall);
            }
        }
    }
}
