package utilities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Monkey implements Comparable<Monkey>{
    private List<Long> items = new ArrayList<>();
    private Integer testDivide;
    private Integer monkeyNumberTrue;
    private Integer monkeyNumberFalse;
    private String stressOperation;
    private String stressFactor;
    private Long inspections = 0L;

    public Monkey copy() {
        Monkey monkey = new Monkey();
        monkey.items = new ArrayList<>(this.items);
        monkey.testDivide = this.testDivide;
        monkey.monkeyNumberTrue = this.monkeyNumberTrue;
        monkey.monkeyNumberFalse = this.monkeyNumberFalse;
        monkey.stressOperation = this.stressOperation;
        monkey.stressFactor = this.stressFactor;
        return monkey;
    }

    private void addItem(long item) {
        items.add(item);
    }

    public void stressItems() {
        items = items.stream().map(this::stressItem).collect(Collectors.toList());
        inspections += items.size();
    }

    private long stressItem(long item) {
        if (stressOperation.equals("*")) {
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

    public void unstressItems() {
        items = items.stream().map(item -> item / 3).collect(Collectors.toList());
    }

    public void passItem(List<Monkey> monkeys, int sumOfModulos) {
        Monkey monkeyTrue = monkeys.get(monkeyNumberTrue);
        Monkey monkeyFalse = monkeys.get(monkeyNumberFalse);
        for(long item : items) {
            if(item % testDivide == 0) {
                monkeyTrue.addItem(item % sumOfModulos);
            } else {
                monkeyFalse.addItem(item % sumOfModulos);
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
        return "" + this.inspections;
    }
}
