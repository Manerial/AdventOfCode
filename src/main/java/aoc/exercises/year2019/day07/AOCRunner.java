package aoc.exercises.year2019.day07;

import aoc.common_objects.RoundDeque;
import utilities.AbstractAOC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2019/day/7">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private List<Integer> memory;

    @Override
    public void run() {
        memory = getMemory(0);

        solution1 = getMaxPower();

        if (isExample) {
            memory = getMemory(1);
        }
        solution2 = getMaxPowerWithFeedback();
    }

    private List<Integer> getMemory(int line) {
        return Stream.of(inputList.get(line).split(","))
                .map(Integer::parseInt)
                .toList();
    }

    /**
     * Get the maximum output power that the computer can produce.
     * This method uses a single amplification run, so when computer A has finished it sends its output to computer B's input.
     * So A -> B -> C -> D -> E, only one time
     *
     * @return The maximum output power that the computer can produce.
     */
    private int getMaxPower() {
        Set<int[]> allConfigurations = getAllConfigurations(new int[]{0, 1, 2, 3, 4});
        int maxOutput = 0;

        for (int[] amplifierConfiguration : allConfigurations) {
            RoundDeque<OpCodeComputer> opCodeComputers = initComputers(amplifierConfiguration, false);
            int power = getPower(opCodeComputers);
            maxOutput = Math.max(power, maxOutput);
        }
        return maxOutput;
    }

    /**
     * Get the maximum output power that the computer can produce.
     * This method uses a multiple amplification run, so when computer A has produced an output it sends its output to computer B's input and wait for a new input.
     * So A -> B -> C -> D -> E -> A -> B -> C ... until the last computer has reach an end.
     *
     * @return The maximum output power that the computer can produce.
     */
    private int getMaxPowerWithFeedback() {
        Set<int[]> allConfigurations = getAllConfigurations(new int[]{5, 6, 7, 8, 9});
        int maxOutput = 0;

        for (int[] amplifierConfiguration : allConfigurations) {
            RoundDeque<OpCodeComputer> opCodeComputers = initComputers(amplifierConfiguration, true);
            int power = getPower(opCodeComputers);
            maxOutput = Math.max(power, maxOutput);
        }
        return maxOutput;
    }

    /**
     * Create a list of amplification computers
     *
     * @param amplifierConfiguration : The amplifier configuration to use for this run.
     * @param shallWaitForInput      : If true, the computers will wait for a new input before computing.
     * @return a list of amplification computers.
     */
    private RoundDeque<OpCodeComputer> initComputers(int[] amplifierConfiguration, boolean shallWaitForInput) {
        RoundDeque<OpCodeComputer> opCodeComputers = new RoundDeque<>();
        for (int amplifier : amplifierConfiguration) {
            OpCodeComputer opCodeComputer = new OpCodeComputer(memory, amplifier, shallWaitForInput);
            opCodeComputers.add(opCodeComputer);
        }
        return opCodeComputers;
    }

    /**
     * Loop on a configuration of amplification computers to get it's output power.
     *
     * @param opCodeComputers : The configuration of the amplification computers.
     * @return the output power of the amplification computers configuration.
     */
    private int getPower(RoundDeque<OpCodeComputer> opCodeComputers) {
        int currentInput = 0;
        OpCodeComputer lastOpCodeComputer = opCodeComputers.getLast();

        while (!lastOpCodeComputer.isFinished()) {
            OpCodeComputer currentOpCodeComputer = opCodeComputers.getFirst();
            currentOpCodeComputer.setComputerInput(currentInput);
            currentOpCodeComputer.compute();
            currentInput = currentOpCodeComputer.getComputerOutput();
            opCodeComputers.rotate(1);
        }

        return currentInput;
    }

    /**
     * Get all possible configurations for the given amplifier index.
     * 01234, 01243, 01324, etc...
     *
     * @param initialConfiguration : The initial configuration to use to get all the other ones.
     * @return all possible configurations for the given amplifier index.
     */
    private static Set<int[]> getAllConfigurations(int[] initialConfiguration) {
        Set<int[]> allConfigurations = new HashSet<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[initialConfiguration.length];
        getAllConfigurationsRecursive(initialConfiguration, allConfigurations, current, used);

        return allConfigurations;
    }

    private static void getAllConfigurationsRecursive(
            int[] initialConfiguration,
            Set<int[]> allConfigurations,
            List<Integer> current,
            boolean[] used
    ) {
        if (current.size() == initialConfiguration.length) {
            int[] newConfiguration = current.stream().mapToInt(Integer::intValue).toArray();
            allConfigurations.add(newConfiguration);
            return;
        }

        for (int i = 0; i < initialConfiguration.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(initialConfiguration[i]);
                getAllConfigurationsRecursive(initialConfiguration, allConfigurations, current, used);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}