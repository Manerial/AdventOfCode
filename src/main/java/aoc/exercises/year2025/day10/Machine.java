package aoc.exercises.year2025.day10;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class Machine {
    private final int totalLights;
    private final BitSet lights;
    private final List<BitSet> buttons;
    private final int[][] vectorButtons;
    private final int[] joltageRequirement;

    public Machine(int numberOfLights, BitSet lights, List<BitSet> lightButtons, int[] joltageRequirement) {
        this.totalLights = numberOfLights;
        this.lights = lights;
        this.buttons = lightButtons;
        this.joltageRequirement = joltageRequirement;

        vectorButtons = new int[buttons.size()][];
        for (int i = 0; i < buttons.size(); i++) {
            int[] vectorButton = toVectorButton(buttons.get(i));
            vectorButtons[i] = vectorButton;
        }
    }

    private int[] toVectorButton(BitSet bitSet) {
        int[] vectorButton = new int[joltageRequirement.length];
        for (int i = 0; i < joltageRequirement.length; i++) {
            if (bitSet.get(i)) {
                vectorButton[i] = 1;
            } else {
                vectorButton[i] = 0;
            }
        }
        return vectorButton;
    }

    public int getMinLightButtonPress() {
        return recursiveGetMinLightButtonPress(0, new BitSet(totalLights), 0);
    }

    private int recursiveGetMinLightButtonPress(int index, BitSet currentBitSet, int currentCount) {
        if (currentBitSet.equals(lights)) {
            return currentCount;
        }
        if (index == buttons.size() + 1) {
            return Integer.MAX_VALUE;
        }
        int lowest = Integer.MAX_VALUE;
        for (int i = index; i < buttons.size(); i++) {
            BitSet bitSetI = buttons.get(i);
            BitSet newBitSet = (BitSet) currentBitSet.clone();
            newBitSet.xor(bitSetI);
            lowest = Math.min(recursiveGetMinLightButtonPress(i + 1, newBitSet, currentCount + 1), lowest);
        }
        return lowest;
    }

    public int getMinJoltageButtonPress() {
        return ILPNaturalSolver.solve(vectorButtons, joltageRequirement);
    }
}
