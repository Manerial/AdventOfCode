package aoc.exercises.year2022.day11;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Monkey implements Comparable<Monkey> {
    private List<Long> items = new ArrayList<>();
    private Integer testDivisorBeforeLaunch;
    private Integer monkeyNumberTrue;
    private Integer monkeyNumberFalse;
    private String stressOperator;
    private String stressFactor;
    private Long inspections = 0L;

    public Monkey() {
    }

    public Monkey(Monkey monkey) {
        this.items = new ArrayList<>(monkey.items);
        this.testDivisorBeforeLaunch = monkey.testDivisorBeforeLaunch;
        this.monkeyNumberTrue = monkey.monkeyNumberTrue;
        this.monkeyNumberFalse = monkey.monkeyNumberFalse;
        this.stressOperator = monkey.stressOperator;
        this.stressFactor = monkey.stressFactor;
        this.inspections = monkey.inspections;
    }

    private void addItem(long item) {
        items.add(item);
    }

    public void stressItems() {
        items = items.stream().map(this::stressItem).toList();
        inspections += items.size();
    }

    private long stressItem(long item) {
        if (stressOperator.equals("*")) {
            return item * getStressFactorI(item);
        } else {
            return item + getStressFactorI(item);
        }
    }

    private long getStressFactorI(long item) {
        if (stressFactor.equals("old")) {
            return item;
        } else {
            return Integer.parseInt(stressFactor);
        }
    }

    public void releaseStress() {
        items = items.stream().map(item -> item / 3).toList();
    }

    /**
     * Give all the items to another monkey from a list.
     * Test the item to know the monkey to throw it.
     * To avoid too big numbers, we apply a modulo.
     *
     * @param monkeys : the list of monkey to use to throw the item
     * @param modulo  : The modulo to use to avoid too big numbers
     */
    public void passItem(List<Monkey> monkeys, int modulo) {
        Monkey monkeyTrue = monkeys.get(monkeyNumberTrue);
        Monkey monkeyFalse = monkeys.get(monkeyNumberFalse);
        for (long item : items) {
            if (item % testDivisorBeforeLaunch == 0) {
                monkeyTrue.addItem(item % modulo);
            } else {
                monkeyFalse.addItem(item % modulo);
            }
        }
        items = new ArrayList<>();
    }

    @Override
    public int compareTo(Monkey u) {
        return inspections.compareTo(u.inspections);
    }

    @Override
    public String toString() {
        return String.valueOf(this.inspections);
    }
}
