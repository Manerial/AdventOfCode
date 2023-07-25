package aoc.exercises.year2022.day10;

import utilities.AbstractAOC;
import utilities.errors.NotAcceptedValue;

import static utilities.ResourceIO.DELIMITER;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2022/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    private Integer tick = 0;
    private Integer sumOfInput = 1;
    private Integer testOutput = 0;
    private Integer spritePosition = 1;
    private String screen = "";

    @Override
    public void run() {
        for (String item : inputList) {
            parseCPUInstruction(item);
        }
        solution1 = testOutput;
        solution2 = screen;
    }

    private void parseCPUInstruction(String item) {
        String instruction = item.split(" ")[0];
        switch (instruction) {
            case "noop" -> doTick(0);
            case "addx" -> {
                int addx = Integer.parseInt(item.split(" ")[1]);
                doTick(0);
                doTick(addx);
            }
            default -> throw new NotAcceptedValue(instruction);
        }
    }


    /**
     * <pre>
     * Start by ticking, then test the screen and print the sprite if it's current position allows it.
     * Then, move the sprite of spriteMove (can be positive or negative).
     * </pre>
     *
     * @param spriteMove : The movement of the sprite
     */
    private void doTick(int spriteMove) {
        tick++;
        testScreen(spriteMove);
        printSprite();
        spritePosition += spriteMove;
    }

    /**
     * <pre>
     * Test the device input to know how it works.
     * Sum the multiplication of the current timer (tick) and the sum of inputs until now.
     * We do this save every 20 ticks
     * </pre>
     *
     * @param input : The input to save
     */
    private void testScreen(int input) {
        if ((tick + 20) % 40 == 0) {
            testOutput += tick * sumOfInput;
        }
        sumOfInput += input;
    }

    private void printSprite() {
        if (tick % 40 == 0 && spritePosition < 38) {
            screen += " ";
        } else if (tick % 40 >= spritePosition && tick % 40 <= spritePosition + 2) {
            screen += "#";
        } else {
            screen += " ";
        }
        if (tick % 40 == 0) {
            screen = screen.trim() + DELIMITER;
        }
    }
}
