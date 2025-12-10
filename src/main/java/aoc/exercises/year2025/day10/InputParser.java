package aoc.exercises.year2025.day10;

import utilities.*;

import java.util.*;

public class InputParser extends AbstractInputParser<List<Machine>> {
    public InputParser(List<String> inputList) {
        super(inputList);
    }

    @Override
    public List<Machine> parseInput() {
        List<Machine> machines = new ArrayList<>();
        for (String line : inputList) {
            String[] machineParts = line
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .replaceAll("\\{", "")
                    .replaceAll("}", "")
                    .split(" ");

            machineParts[0] = machineParts[0]
                    .replaceAll("\\.", "0")
                    .replaceAll("#", "1");
            int numberOfLights = machineParts[0].length();
            BitSet lights = toBitSet(machineParts[0], numberOfLights);

            List<BitSet> buttons = new ArrayList<>(Arrays.stream(machineParts)
                    .skip(1)
                    .limit(machineParts.length - 2)
                    .map(part -> Arrays.stream(part.split(",")).map(Integer::parseInt).toList())
                    .map(part -> getLightSwitch(part, numberOfLights))
                    .toList());

            int[] joltageRequirement = Arrays.stream(machineParts[machineParts.length - 1].split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Machine machine = new Machine(numberOfLights, lights, buttons, joltageRequirement);
            machines.add(machine);
        }
        return machines;
    }

    private BitSet toBitSet(String str, int length) {
        BitSet bitSet = new BitSet(length);
        for (int index = 0; index < length; index++) {
            if (str.charAt(index) == '1') {
                bitSet.set(index);
            }
        }
        return bitSet;
    }

    private BitSet getLightSwitch(List<Integer> part, int length) {
        BitSet bitSet = new BitSet(length);
        for (Integer index : part) {
            bitSet.set(index);
        }
        return bitSet;
    }
}
