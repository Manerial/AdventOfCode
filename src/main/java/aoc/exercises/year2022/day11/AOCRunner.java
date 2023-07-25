package aoc.exercises.year2022.day11;

import utilities.AbstractAOC;
import utilities.errors.NotAcceptedValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/11">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private static final int ROUNDS = 20;
    private static final int ROUNDS_NO_DIV = 10000;
    private final List<Monkey> monkeys = new ArrayList<>();
    private final List<Monkey> monkeys2 = new ArrayList<>();
    private int sumOfModules;

    @Override
    public void run() {
        for (String item : inputList) {
            parseLine(item);
        }
        // This is the trickiest part to comprehend :D
        // We can manage a simple long as item using the multiplication of all the dividers factors.
        // This will be used as a modulo before sending item to another monkey
        sumOfModules = monkeys.stream().map(Monkey::getTestDivisorBeforeLaunch).reduce((a, b) -> a * b).orElse(0);
        // copy all the monkeys to another list to avoid a new parsing of our input
        monkeys.forEach(monkey -> monkeys2.add(new Monkey(monkey)));
        playRounds();
        playRoundsNoDivision();
        Collections.sort(monkeys);
        Collections.sort(monkeys2);
        Collections.reverse(monkeys);
        Collections.reverse(monkeys2);
        solution1 = monkeys.stream().limit(2).map(Monkey::getInspections).reduce((a, b) -> a * b).orElse(0L);
        solution2 = monkeys2.stream().limit(2).map(Monkey::getInspections).reduce((a, b) -> a * b).orElse(0L);
    }

    private void playRounds() {
        for (int round = 1; round <= ROUNDS; round++) {
            for (Monkey monkey : monkeys) {
                monkey.stressItems();
                monkey.releaseStress();
                monkey.passItem(monkeys, sumOfModules);
            }
        }
    }

    private void playRoundsNoDivision() {
        for (int round = 1; round <= ROUNDS_NO_DIV; round++) {
            for (Monkey monkey : monkeys2) {
                monkey.stressItems();
                monkey.passItem(monkeys2, sumOfModules);
            }
        }
    }

    /**
     * <pre>
     * Parse the first word of a line to choose what to do.
     * Example : If line starts with "Monkey", add a new monkey to the list
     * </pre>
     *
     * @param line : the line to parse and to send to the function
     */
    private void parseLine(String line) {
        String word = line.trim().split(" ")[0].replace(":", "");
        switch (word) {
            case "Monkey" -> addMonkey();
            case "Starting" -> addStartItems(line);
            case "Operation" -> addOperation(line);
            case "Test" -> addTest(line);
            case "If" -> addIfTest(line);
            case "" -> {
                // nothing
            }
            default -> throw new NotAcceptedValue(word);
        }
    }

    /**
     * <pre>
     * Just add a monkey to a list
     * </pre>
     */
    private void addMonkey() {
        Monkey monkey = new Monkey();
        monkeys.add(monkey);
    }

    /**
     * <pre>
     * Parse a specific line to add new items to a monkey
     * Example : Starting items: 79, 98
     * This will add 79 and 98 to a monkey
     * </pre>
     *
     * @param line : the line to parse
     */
    private void addStartItems(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        String[] list = line.split(": ")[1].split(", ");
        for (String item : list) {
            monkey.getItems().add(Long.parseLong(item));
        }
    }

    /**
     * <pre>
     * Parse a specific line to add stressOperator and a stressFactor to a monkey
     * Example 1 : Operation: new = old * 19
     * This register * as stressOperator and 19 as stressFactor
     * Example 2 : Operation: new = old + old
     * This register + as stressOperator and old (current item value) as stressFactor
     * </pre>
     *
     * @param line : the line to parse
     */
    private void addOperation(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        String[] operation = line.split("old ")[1].split(" ");
        monkey.setStressOperator(operation[0]);
        monkey.setStressFactor(operation[1]);
    }

    /**
     * <pre>
     * Parse a specific line to add testDivisorBeforeLaunch
     * Example : Test: divisible by 23
     * This register 23 as testDivisorBeforeLaunch
     * </pre>
     *
     * @param line : the line to parse
     */
    private void addTest(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        int operation = Integer.parseInt(line.split("by ")[1]);
        monkey.setTestDivisorBeforeLaunch(operation);
    }

    /**
     * <pre>
     * Parse a specific line to set the monkey to throw the item if the test is true/false
     * The test is : Can the item be divided by testDivisorBeforeLaunch ?
     * Example : If true: throw to monkey 2
     * This register 2 as the monkey to throw the item if the test is true
     * Example : If true: throw to monkey 3
     * This register 3 as the monkey to throw the item if the test is false
     * </pre>
     *
     * @param line : the line to parse
     */
    private void addIfTest(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        int monkeyNumber = Integer.parseInt(line.split("monkey ")[1]);
        if (line.contains("true")) {
            monkey.setMonkeyNumberTrue(monkeyNumber);
        } else {
            monkey.setMonkeyNumberFalse(monkeyNumber);
        }
    }
}
