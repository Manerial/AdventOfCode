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
            sumOfModulos = monkeys.stream().map(Monkey::getTestDivide).reduce((a, b) -> a * b).orElse(0);
            monkeys.forEach(monkey -> monkeys2.add(monkey.copy()));
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
                monkey.unstressItems();
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

    private void parseLine(String item) {
        String operande = item.trim().split(" ")[0];
        switch (operande) {
            case "Monkey":
                addMonkey(item);
                break;
            case "Starting":
                addStartings(item);
                break;
            case "Operation:":
                addOperation(item);
                break;
            case "Test:":
                addTest(item);
                break;
            case "If":
                addIfTest(item);
                break;
            case "":
                break;
            default:
                break;
        }
    }

    // Monkey 0:
    private void addMonkey(String line) {
        Monkey monkey = new Monkey();
        monkeys.add(monkey);
    }

    //Starting items: 79, 98
    private void addStartings(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        String[] list = line.split(": ")[1].split(", ");
        for (String item : list) {
            monkey.getItems().add(Long.parseLong(item));
        }
    }

    // Operation: new = old * 19
    private void addOperation(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        String[] operation = line.split("old ")[1].split(" ");
        monkey.setStressOperation(operation[0]);
        monkey.setStressFactor(operation[1]);
    }

    // Test: divisible by 23
    private void addTest(String line) {
        Monkey monkey = monkeys.get(monkeys.size() - 1);
        int operation = Integer.parseInt(line.split("by ")[1]);
        monkey.setTestDivide(operation);
    }


    // If true: throw to monkey 2
    // If false: throw to monkey 3
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
