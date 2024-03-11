package aoc.exercises.year2016.day10;

import org.apache.commons.lang3.tuple.Pair;
import utilities.AbstractAOC;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Instructions are <a href="https://adventofcode.com/2016/day/10">here</a>
 * </pre>
 */
public class AOCRunner extends AbstractAOC {
    Map<Integer, Bot> bots = new HashMap<>();
    Map<Integer, Integer> outputs = new HashMap<>();

    @Override
    public void run() {
        InputParser inputParser = new InputParser(inputList);
        bots = inputParser.parseInput();

        playBotsInstructions();

        if (isExample) {
            solution1 = 0;
        } else {
            solution1 = bots.values().stream()
                    .filter(bot -> bot.hasCompared("1761"))
                    .findAny()
                    .orElseThrow()
                    .getId();
        }
        solution2 = outputs.get(0) * outputs.get(1) * outputs.get(2);
    }

    /**
     * Each bot that has two chips will play its instructions and distribute the chips to its planned destinations (bot / output).
     */
    private void playBotsInstructions() {
        Bot firstBot = bots.values().stream()
                .filter(Bot::hasTwoChips)
                .findAny()
                .orElse(null);

        Deque<Bot> botsWithTwoChips = new ArrayDeque<>();
        botsWithTwoChips.add(firstBot);

        while (!botsWithTwoChips.isEmpty()) {
            Bot currentBot = botsWithTwoChips.pop();
            Pair<Bot, Bot> lowAndHigh = currentBot.compareAndGive(bots, outputs);
            Bot lowBot = lowAndHigh.getLeft();
            Bot highBot = lowAndHigh.getRight();
            if (lowBot != null && lowBot.hasTwoChips()) {
                botsWithTwoChips.add(lowBot);
            }
            if (highBot != null && highBot.hasTwoChips()) {
                botsWithTwoChips.add(highBot);
            }
        }
    }
}