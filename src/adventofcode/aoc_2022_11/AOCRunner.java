package adventofcode.aoc_2022_11;

import template.AOC;
import utilities.FileLoader;
import utilities.Monkey;
import utilities.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AOCRunner implements AOC {
    private final List<Monkey> monkeys = new ArrayList<>();
    private final List<Monkey> monkeys2 = new ArrayList<>();

    private int sumOfModulos;

    @Override
    public void run(String file) {
        try {
            List<String> list = FileLoader.readListFromFile(file);
            for (String item : list) {
                parseLine(item);
            }
            // This is the trickiest part to comprehend :D
            // We can manage a simple long as item using the multiplication of all the dividers factors.
            // This will be used as a modulo before sending item to another monkey
            sumOfModulos = monkeys.stream().map(Monkey::getTestDivisorBeforeLaunch).reduce((a, b) -> a * b).orElse(0);
            // copy all the monkeys to another list to avoid a new parsing of our input
            monkeys.forEach(monkey -> monkeys2.add(new Monkey(monkey)));
            playRounds(20);
            playRoundsNoDivision(10000);
            Collections.sort(monkeys);
            Collections.sort(monkeys2);
            Collections.reverse(monkeys);
            Collections.reverse(monkeys2);
            long solution1 = monkeys.stream().limit(2).map(Monkey::getInspections).reduce((a, b) -> a * b).orElse(0L);
            long solution2 = monkeys2.stream().limit(2).map(Monkey::getInspections).reduce((a, b) -> a * b).orElse(0L);
            Printer.println("Solution 1 : " + solution1);
            Printer.println("Solution 2 : " + solution2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playRounds(int rounds) {
        for (int round = 1; round <= rounds; round++) {
            for (Monkey monkey : monkeys) {
                monkey.stressItems();
                monkey.releaseStress();
                monkey.passItem(monkeys, sumOfModulos);
            }
        }
    }

    private void playRoundsNoDivision(int rounds) {
        for (int round = 1; round <= rounds; round++) {
            for (Monkey monkey : monkeys2) {
                monkey.stressItems();
                monkey.passItem(monkeys2, sumOfModulos);
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
            case "Monkey":
                addMonkey();
                break;
            case "Starting":
                addStartItems(line);
                break;
            case "Operation":
                addOperation(line);
                break;
            case "Test":
                addTest(line);
                break;
            case "If":
                addIfTest(line);
                break;
            default:
                break;
        }
    }

    /**<pre>
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
