package aoc.exercises.year2016.day10;

import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

@Getter
public class Bot {
    private final int id;
    private Integer chip1;
    private Integer chip2;
    private String compared = null;
    private final boolean lowToBot;
    private final boolean highToBot;
    private final int lowDestination;
    private final int highDestination;

    public Bot(int id, boolean lowToBot, boolean highToBot, int lowDestination, int highDestination) {
        this.id = id;
        this.lowToBot = lowToBot;
        this.highToBot = highToBot;
        this.lowDestination = lowDestination;
        this.highDestination = highDestination;
    }

    /**
     * Return whether this bot has two chips or not.
     *
     * @return true if this bot has two chips, false otherwise.
     */
    public boolean hasTwoChips() {
        return chip1 != null && chip2 != null;
    }

    /**
     * Givrs a chip to this bot.
     *
     * @param chip : the chip to give to this bot
     */
    public void takeChip(Integer chip) {
        if (chip1 == null) {
            chip1 = chip;
        } else {
            chip2 = chip;
        }
    }

    /**
     * Compare the two chips and give the lowest one to the lowDestination bot/output
     * and the highest one to the highDestination bot/output.
     *
     * @param bots    : all the bots that were created
     * @param outputs : all the outputs that were created
     * @return a pair of bots that received the lowest and highest chips
     */
    public Pair<Bot, Bot> compareAndGive(Map<Integer, Bot> bots, Map<Integer, Integer> outputs) {
        Integer lowChip = Math.min(chip1, chip2);
        Integer highChip = Math.max(chip1, chip2);

        Bot lowBot = give(bots, outputs, lowDestination, lowToBot, lowChip);
        Bot highBot = give(bots, outputs, highDestination, highToBot, highChip);

        compared = lowChip + "" + highChip;

        chip1 = null;
        chip2 = null;

        return new ImmutablePair<>(lowBot, highBot);
    }

    private Bot give(Map<Integer, Bot> bots, Map<Integer, Integer> outputs, int destination, boolean useBot, Integer chip) {
        Bot bot = null;
        if (useBot) {
            bot = bots.get(destination);
            bot.takeChip(chip);
        } else {
            outputs.put(destination, chip);
        }
        return bot;
    }

    public boolean hasCompared(String compared) {
        return compared.equals(this.compared);
    }
}
