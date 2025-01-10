package aoc.exercises.year2024.day24;

import utilities.AbstractInputParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParser extends AbstractInputParser<Map<String, BooleanGate>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public Map<String, BooleanGate> parseInput() {
        Map<String, BooleanGate> gatesByNames = new HashMap<>();
        inputList.forEach(line -> {
            if (line.contains(":")) {
                extractGate(line, gatesByNames);
            } else if (line.contains("->")) {
                extractGateWithOperator(line, gatesByNames);
            }
        });
        return gatesByNames;
    }

    private void extractGate(String line, Map<String, BooleanGate> gatesByNames) {
        String[] parts = line.split(": ");
        String gateName = parts[0];
        String gateValue = parts[1];
        BooleanGate gate = new BooleanGate(gateName);
        gate.setOutput(gateValue.equals("1"));
        gatesByNames.put(gateName, gate);
    }

    private static void extractGateWithOperator(String line, Map<String, BooleanGate> gatesByNames) {
        String[] parts = line.split(" ");
        String firstGate = parts[0];
        String operator = parts[1];
        String secondGate = parts[2];
        String gateName = parts[4];
        BooleanGate gate1 = gatesByNames.getOrDefault(firstGate, new BooleanGate(firstGate));
        BooleanGate gate2 = gatesByNames.getOrDefault(secondGate, new BooleanGate(secondGate));
        BooleanGate currentGate = gatesByNames.getOrDefault(gateName, new BooleanGate(gateName));

        currentGate.setOperator(Operator.valueOf(operator));
        currentGate.setGate1(gate1);
        currentGate.setGate2(gate2);

        gatesByNames.putIfAbsent(firstGate, gate1);
        gatesByNames.putIfAbsent(secondGate, gate2);
        gatesByNames.putIfAbsent(gateName, currentGate);
    }
}
