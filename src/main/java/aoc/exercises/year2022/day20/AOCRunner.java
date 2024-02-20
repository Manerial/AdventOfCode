package aoc.exercises.year2022.day20;

import aoc.common_objects.RoundDeque;
import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/20">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Pair<Long, Integer> itemZero;
    Pair<Long, Integer> lastItem;

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        RoundDeque<Pair<Long, Integer>> list = inputParser.parseInput();
        int listSize = list.size();
        initItems(list, listSize);
        move(list);
        list.goTo(itemZero);
        solution1 = round1000ThenGet(list) + round1000ThenGet(list) + round1000ThenGet(list);

        inputParser.setDecryptionKey(811589153);
        list = inputParser.parseInput();
        initItems(list, listSize);
        for (int i = 0; i < 10; i++) {
            move(list);
        }
        list.goTo(itemZero);
        solution2 = round1000ThenGet(list) + round1000ThenGet(list) + round1000ThenGet(list);
    }

    private void initItems(RoundDeque<Pair<Long, Integer>> list, int listSize) {
        itemZero = list.stream().filter(p -> p.getKey() == 0).findAny().orElseThrow();
        lastItem = list.stream().filter(p -> p.getValue() == listSize - 1).findAny().orElseThrow();
    }

    private long round1000ThenGet(RoundDeque<Pair<Long, Integer>> list) {
        list.rotate(1000);
        return list.getFirst().getKey();
    }

    private void move(RoundDeque<Pair<Long, Integer>> list) {
        for (int i = 0; i <= lastItem.getValue(); i++) {
            while (i != list.getFirst().getValue()) {
                list.rotate(1);
            }
            Pair<Long, Integer> currentItem = list.removeFirst();
            long rotation = currentItem.getKey();
            list.rotate(rotation);
            list.addFirst(currentItem);
        }
    }
}