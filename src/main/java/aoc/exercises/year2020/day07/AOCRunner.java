package aoc.exercises.year2020.day07;

import utilities.AbstractAOC;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2020/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        Set<Bag> bags = new HashSet<>(inputParser.parseInput());
        Bag shinyBag = bags.stream()
                .filter(bag -> bag.getName().equals("shiny gold"))
                .findFirst()
                .orElseThrow();

        solution1 = bags.stream()
                .filter(bag -> bag.canContain(shinyBag))
                .count();

        solution2 = shinyBag.countAllBags();
    }
}
