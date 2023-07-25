package aoc.exercises.year2022.day17;

import utilities.AbstractAOC;
import aoc.common_objects.Position;
import aoc.common_objects.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/17">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    record FallingRock(Shape rock, boolean hasFallen){}
    private static final long BIG_NB_ROCKS = 1000000000000L;
    private static final int NB_ROCKS = 2022;
    private final BlockTower blockTower = new BlockTower(Position.interval(new Position(0, 0), new Position(6, 0)));
    private final List<LoopInfo> blocksInLoop = new ArrayList<>();
    private RockShape currentRockShape = RockShape.R1;

    @Override
    public void run() {

        char[] directions = inputList.get(0).toCharArray();
        generateBlockTower(directions);

        solution1 = (blockTower.size());

        int loopSize = getLoopInfo(LoopInfo::getRockIndex);
        int nbBlocsInLoop = getLoopInfo(LoopInfo::getBlocks);

        LoopInfo loopInfo = getFirstLoopIndexFromEnd(loopSize);
        long nbLoops = ((BIG_NB_ROCKS - loopInfo.getRockIndex()) / loopSize);

        int sizeBeforeLoop = loopInfo.getBlocks();
        long sizeOfLoop = nbLoops * nbBlocsInLoop;
        solution2 = sizeBeforeLoop + sizeOfLoop;
    }

    private void generateBlockTower(char[] directions) {
        int directionIndex = 0;
        for (int rockIndex = 1; rockIndex <= NB_ROCKS; rockIndex++) {
            FallingRock fallingRock = new FallingRock(generateRock(),false);
            while (!fallingRock.hasFallen) {
                char direction = directions[directionIndex];
                directionIndex = (directionIndex + 1) % directions.length;
                fallingRock = blowAndFall(fallingRock, direction);
            }
            blockTower.add(fallingRock.rock);
            manageBlocksInLoop(directionIndex, fallingRock.rock, rockIndex);
            currentRockShape = currentRockShape.next();
        }
    }

    private Shape generateRock() {
        int xOffset = 2;
        int yOffset = blockTower.size() + 4;
        return currentRockShape.spawnRock(xOffset, yOffset);
    }


    private FallingRock blowAndFall(FallingRock fallingRock, char direction) {
        Shape rockBeforeAll = fallingRock.rock;
        Shape rockAfterBlow = blow(rockBeforeAll, direction);
        Shape rockAfterFall = fall(rockAfterBlow);
        boolean hasFallen = rockAfterFall.getMinY() == rockBeforeAll.getMinY();
        return new FallingRock(rockAfterFall, hasFallen);
    }

    private Shape blow(Shape currentShape, char direction) {
        int xOffset = (direction == '<') ? -1 : 1;
        int yOffset = 0;
        Shape shapeAfterBlow = currentShape.copyWithOffset(xOffset, yOffset);
        if (shapeAfterBlow.getMinX() >= 0 && shapeAfterBlow.getMaxX() < blockTower.getWidth() && disjoints(shapeAfterBlow)) {
            return shapeAfterBlow;
        } else {
            return currentShape;
        }
    }

    private Shape fall(Shape currentShape) {
        Shape shapeAfterFall = currentShape.copyWithOffset(0, -1);
        if (disjoints(shapeAfterFall)) {
            return shapeAfterFall;
        } else {
            return currentShape;
        }
    }

    private boolean disjoints(Shape shape) {
        return Collections.disjoint(new ArrayList<>(shape.getPositions()), blockTower.getBlocks());
    }

    private void manageBlocksInLoop(int directionIndex, Shape currentShape, int rockIndex) {
        LoopInfo loopInfo = new LoopInfo(currentRockShape, directionIndex, currentShape.getMinX(), blockTower.size(), rockIndex);
        blocksInLoop.add(loopInfo);
    }

    private LoopInfo getFirstLoopIndexFromEnd(int loopSize) {
        int index = (int) ((BIG_NB_ROCKS - 1) % loopSize);
        while (index < blocksInLoop.size()) {
            index += loopSize;
        }
        return blocksInLoop.get(index - loopSize);
    }

    private int getLoopInfo(Function<LoopInfo, Integer> callback) {
        LoopInfo loopInfo = getLastElement();
        List<Integer> blocksList = blocksInLoop.stream()
                .filter(loopInfo1 -> loopInfo1.matches(loopInfo))
                .map(callback)
                .toList();
        int blocks1 = blocksList.get(blocksList.size() - 1);
        int blocks2 = blocksList.get(blocksList.size() - 2);
        return blocks1 - blocks2;
    }

    private LoopInfo getLastElement() {
        return blocksInLoop.get(blocksInLoop.size() - 1);
    }
}
